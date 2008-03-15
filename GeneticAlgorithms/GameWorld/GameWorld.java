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
		this.loadRandomWorld(1, 100, 100, 50);
		
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
	
	public void addWumpus(int x,int y){
		
		Actor hunter = new Actor(x,y,ObjectType.WUMPUS,true);
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
		this.addWumpus(2, 2);
		
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
	
	public void loadRandomWorld(int wumpus,int prey,int food,int hunter){
		
		Random rand = new Random();
		int random = 0;
		
		if ((random + wumpus + prey + food) <= 10000){
		
			for(int i = 1;i < (width - 1);i++){
				for (int j = 1;j <(height - 1);j++){
					
					random = rand.nextInt(10000);
					
					System.out.println(random);
					
					if (random < wumpus){
						this.addWumpus(i, j);
					}else if (random >= wumpus && random < (wumpus + prey)){
						this.addPrey(i, j);
					}else if (random >= (wumpus + prey) && random < (wumpus + prey + food)){
						this.addFood(i, j);
					}else if (random >= (wumpus + prey + food) && random < (wumpus + prey + food + hunter)){
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
	
	
	public void simulateActor(Actor actor,Action action){
		
		switch (action){
		
		case UP:
			world[actor.getX()][actor.getY()] = new GameObject(actor.getX(),actor.getY(),ObjectType.NONE,false);
			world[actor.getX()][actor.getY() -1] = actor;
			actor.setY(actor.getY() -1);
		break;
		case DOWN:
			world[actor.getX()][actor.getY()] = new GameObject(actor.getX(),actor.getY(),ObjectType.NONE,false);
			world[actor.getX()][actor.getY() +1] = actor;
			actor.setY(actor.getY() + 1);
		break;
		case LEFT:
			world[actor.getX()][actor.getY()] = new GameObject(actor.getX(),actor.getY(),ObjectType.NONE,false);
			world[actor.getX() - 1][actor.getY()] = actor;
			actor.setX(actor.getX() - 1);
		break;
		case RIGHT:
			world[actor.getX()][actor.getY()] = new GameObject(actor.getX(),actor.getY(),ObjectType.NONE,false);
			world[actor.getX() + 1][actor.getY()] = actor;
			actor.setX(actor.getX() + 1);
		break;
		case NONE:
			break;
		}
	}
	
	public boolean validEat(Actor actor, Action a){
		
		GameObject prey = new GameObject(0,0,ObjectType.NONE,false);
		
		ObjectType actorType = actor.getType();
		
		if (prey.getType() == ObjectType.WALL){
			return false;
		}else if(!prey.isAlive()){
			return false;
		}else if (prey.getType() == ObjectType.NONE){
			return false;
		}else if (actorType == ObjectType.PREY){
			
			if (prey.getType() != ObjectType.FOOD){
				return false;
			}else {
				return true;
			}
			
		}else if (actorType == ObjectType.HUNTER){
			
			if (prey.getType() != ObjectType.PREY){
				return false;
			}else {
				return true;
			}
			
		}else if (actorType == ObjectType.WUMPUS){
			
			if (prey.getType() != ObjectType.WALL || prey.getType() != ObjectType.NONE){
				return true;
			}else{
				return false;
			}
			
		}
		
		return false;
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
