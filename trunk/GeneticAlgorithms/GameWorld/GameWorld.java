package GameWorld;

import java.util.ArrayList;
import actors.*;

import java.util.Random;
import rules.*;



public class GameWorld {

	
	private int width;
	private int height;
	
	private GameObject[][] world;
	private ArrayList<Actor> actors;
	
	
	public GameWorld(int width, int height){
		
		this.width = width;
		this.height = height;
		this.world = new GameObject[width][height];
		this.actors = new ArrayList<Actor>();
		this.initWorld();
		this.loadRandomWorld(100, 100, 50);
		
	}
	
	public void addPrey(int x, int y){
		
		Actor prey = new Actor(x,y,ObjectType.PREY,true);
		world[x][y] = prey;
		actors.add(prey);
		
	}
	
	public void addHunter(int x,int y){
		
		Actor hunter = new Actor(x,y,ObjectType.HUNTER,true);
		world[x][y] = hunter;
		actors.add(hunter);
		
	}
	
	public void addFood(int x,int y){
		
		Actor food = new Actor(x,y,ObjectType.FOOD,true);
		world[x][y] = food;
		actors.add(food);
		
	}
	
	public void initWorld(){
		
		for (int i = 0;i < this.width;i++){
			for (int j = 0;j <height;j++){
				
				if (i == 0 || j == 0 || i == (width -1) || j == (height-1)){
					
					GameObject wall = new GameObject(i,j,ObjectType.WALL,false);
					world[i][j] = wall;
					
				}else{
					GameObject none = new GameObject(i,j,ObjectType.NONE,false);
					world[i][j] = none;
					
				}
			}
		}
		
	}
	
	public void loadWorld1(){
		
		this.addHunter(4, 4);
		this.addFood(10, 10);
		this.addPrey(6, 6);
		this.addPrey(7, 8);
		this.addPrey(8, 9);
		this.addPrey(10, 2);
		this.addPrey(2, 5);
		this.addPrey(5, 6);
		this.addPrey(6, 9);
		this.addPrey(15, 4);
		this.addHunter(20, 20);
		
	}
	
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public GameObject[][] getWorld(){
		
		return this.world;
		
	}
	
	public void loadRandomWorld(int prey,int food,int hunter){
		
		Random rand = new Random();
		int random = 0;
		
		if ((random + prey + food) <= 10000){
		
			for(int i = 1;i < (width - 1);i++){
				for (int j = 1;j <(height - 1);j++){
					
					random = rand.nextInt(10000);
					
					if (random < prey){
						this.addPrey(i, j);
					}else if (random >= prey && random < (prey + food)){
						this.addFood(i, j);
					}else if (random >= (prey + food) && random < (prey + food + hunter)){
						this.addHunter(i, j);
					}
				}
			}
		}else{
			this.loadWorld1();
		}
	}
	
	public ArrayList<Actor> getActors(){
			
		return this.actors;
		
	}
	public void doAction(Actor actor, int dx, int dy) {
		GameObject other = world[actor.getX() + dx][actor.getY() + dy];
		if (other.getType() == ObjectType.NONE) {
			world[actor.getX()][actor.getY()] = new GameObject(actor.getX(),actor.getY(),ObjectType.NONE,false);
			world[actor.getX() + dx][actor.getY() + dy] = actor;
			actor.setX(actor.getX() + dx);
			actor.setY(actor.getY() + dy);
		} //else if (other.getType() == actor.getType()) {
			//mate
		//} else if () {
			
		//} //else wall : nothing
	}
	public void simulateActor(Actor actor,Action action){
		
		switch (action){
		
		case UP:
			this.doAction(actor, 0, -1);	
		break;
		case DOWN:
			this.doAction(actor, 0, 1);
		break;
		case LEFT:
			this.doAction(actor, -1, 0);
		break;
		case RIGHT:
			this.doAction(actor, 1, 0);
		break;
		case NONE:
			break;
		}
	}
	
	public void randomizeList(ArrayList<Actor> actorList){
		
		Random rand = new Random();
		ArrayList<Actor> newList = new ArrayList<Actor>();
		int length = actorList.size();
		
		for (int i = 0;i <length;i++){
			
			int random = rand.nextInt(actorList.size());
			
			Actor current = actorList.remove(random);
			newList.add(current);
			
		}
		
		this.actors = newList;
	}
	
	public void removeKilled(ArrayList<Actor> actorList){
		
		ArrayList<Actor> newList = new ArrayList<Actor>();
		
		for (int i =0;i <actorList.size();i++){
			
			if (actorList.get(i).isAlive()){
				newList.add(actorList.get(i));
			}else{
				Actor a = actorList.get(i);
				world[a.getX()][a.getY()] = new GameObject(a.getX(),a.getY(),ObjectType.NONE,false);
			}
			
		}
		
		this.actors = newList;
		
	}
	
	public void tick(){
		
		this.randomizeList(this.actors);
		
		for (int i = 0;i < actors.size();i++){
			
			Actor current = actors.get(i);
			Perception percept = new Perception(current.getX(),current.getY(),world);
			Action a = current.getNextAction(percept);
			this.simulateActor(current, a);
			
		}
		
		this.removeKilled(this.actors);
		
	}
	
}
