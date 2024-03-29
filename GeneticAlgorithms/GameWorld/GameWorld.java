package GameWorld;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

import rules.Action;
import actors.Actor;
import actors.GameObject;
import actors.ObjectType;

public class GameWorld {

	private HashMap<String, Integer> map;

	private Random rand;

	private int width;

	private int height;

	private int turnCount = 0;

	private int generation = 0;

	private int regenRate;// = 30; // Chance that a piece of food will be

	// spawned
	private int regenTurn;// = 10; // How many turns until food is spawned

	private int regenCounter;// = regenTurn; // Counter for food respawn

	private int preyEnergy;// = 250;

	private int hunterEnergy;// = 1;

	private int foodEnergy;// = 300;

	private int energyGained;// = 30;

	private int respawnLevel;

	private GameObject[][] world;

	private ArrayList<Actor> actors;

	private int preyCount = 0;

	private PriorityQueue<Actor> dead;

	private int ticks = 0;
	
	private String myFile = "genetic2.txt";

	// public GameWorld(int width, int height) {
	//
	// this.rand = new Random();
	// this.width = width;
	// this.height = height;
	// this.world = new GameObject[width][height];
	// this.actors = new ArrayList<Actor>();
	// this.initWorld();
	// this.loadRandomWorld(preyCount, foodCount, hunterCount, rockCount);
	//
	// }

	public GameWorld(HashMap<String, Integer> map) {

		this.map = map;
		this.rand = new Random();
		width = map.get("width");
		height = map.get("height");

		this.regenRate = map.get("regenRate");
		this.regenTurn = map.get("regenTurn");

		this.preyEnergy = map.get("preyEnergy");
		this.hunterEnergy = map.get("hunterEnergy");
		this.foodEnergy = map.get("foodEnergy");

		this.energyGained = map.get("energyGained");

		this.respawnLevel = map.get("respawnLevel");

		regenCounter = regenTurn;

		this.dead = new PriorityQueue<Actor>();
		
		this.initWorld();
		this.loadRandomWorld(map.get("preyCount"), map.get("foodCount"), map
				.get("hunterCount"), map.get("rockCount"));
	}

	public void spawnNewFood() {

		int random;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				random = rand.nextInt(10000);

				if (world[i][j].getType() == ObjectType.NONE) {
					if (random < regenRate) {
						this.addFood(i, j);
					}
				}
			}
		}
	}

	public void addPrey(int x, int y) {

		Actor prey = new Actor(x, y, ObjectType.PREY, true, this.preyEnergy);
		world[x][y] = prey;
		actors.add(prey);
		preyCount++;

	}

	public void addWall(int x, int y) {

		GameObject obj = new GameObject(x, y, ObjectType.WALL, false);
		world[x][y] = obj;

	}

	public void addHunter(int x, int y) {

		Actor hunter = new Actor(x, y, ObjectType.HUNTER, true,
				this.hunterEnergy);
		world[x][y] = hunter;
		actors.add(hunter);

	}

	public void addFood(int x, int y) {

		Actor food = new Actor(x, y, ObjectType.FOOD, true, this.foodEnergy);
		world[x][y] = food;
		actors.add(food);

	}

	public void initWorld() {

		this.world = new GameObject[width][height];
		this.actors = new ArrayList<Actor>();
//		this.dead = new PriorityQueue<Actor>();

		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < height; j++) {

				GameObject none = new GameObject(i, j, ObjectType.NONE, false);
				world[i][j] = none;

			}
		}
	}

	public void addRock(int x, int y) {

		int random = rand.nextInt(8);

		for (int i = 0; i < random; i++) {

			if (world[x][y].getType() == ObjectType.NONE) {
				this.addWall(x, y);
			}

			x = translateX(x + rand.nextInt(3) - 1);
			y = translateY(y + rand.nextInt(3) - 1);

		}

	}

	public void loadWorld1() {

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

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public GameObject[][] getWorld() {

		return this.world;

	}

	public void loadRandomWorld(int prey, int food, int hunter, int rocks) {

		int random = 0;

		if ((random + prey + food) <= 10000) {

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {

					random = rand.nextInt(10000);

					if (random < prey) {
						this.addPrey(i, j);
					} else if (random >= prey && random < (prey + food)) {
						this.addFood(i, j);
					} else if (random >= (prey + food)
							&& random < (prey + food + hunter)) {
						this.addHunter(i, j);
					} else if (random >= (prey + food + hunter)
							&& random < (prey + food + hunter + rocks)) {
						this.addRock(i, j);
					}
				}
			}

		} else {
			this.loadWorld1();
		}
	}

	public void loadNextWorld(ArrayList<Actor> list) {

		int prey = map.get("preyCount");
		int food = map.get("foodCount");
		int hunter = map.get("hunterCount");
		int rocks = map.get("rockCount");

		this.initWorld();

		for (int i = 0; i < list.size(); i++) {

			Actor a = list.get(i);

			world[a.getX()][a.getY()] = a;
			actors.add(a);

		}

		prey = prey / 2;

		int random = 0;

		if ((random + prey + food) <= 10000) {

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {

					random = rand.nextInt(10000);

					if (random < prey) {
						this.addPrey(i, j);
					} else if (random >= prey && random < (prey + food)) {
						this.addFood(i, j);
					} else if (random >= (prey + food)
							&& random < (prey + food + hunter)) {
						this.addHunter(i, j);
					} else if (random >= (prey + food + hunter)
							&& random < (prey + food + hunter + rocks)) {
						this.addRock(i, j);
					}
				}
			}

		} else {
			this.loadWorld1();
		}
	}

	public ArrayList<Actor> getActors() {

		return this.actors;

	}

	public void doAction(Actor actor, int dx, int dy) {

		GameObject other = world[translateX(actor.getX() + dx)][translateY(actor
				.getY()
				+ dy)];

		if (actor.getType() == ObjectType.PREY
				|| actor.getType() == ObjectType.FOOD) {
			actor.subtractEnergy(1);
		}

		if (other.getType() == ObjectType.NONE && !(dx == 0 && dy == 0)) {
			world[actor.getX()][actor.getY()] = new GameObject(actor.getX(),
					actor.getY(), ObjectType.NONE, false);
			world[translateX(actor.getX() + dx)][translateY(actor.getY() + dy)] = actor;

			if (actor.getType() == ObjectType.PREY
					&& actor.getEnergy() > respawnLevel) {
				Actor child = actor.spawn();
				world[translateX(actor.getX())][translateY(actor.getY())] = child;
				child.setEnergy(respawnLevel);
				actor.setEnergy(actor.getEnergy() - respawnLevel);
				this.actors.add(child);

				if (actor.getType() == ObjectType.PREY) {
					preyCount++;
				}

			}

			actor.setX(translateX(actor.getX() + dx));
			actor.setY(translateY(actor.getY() + dy));

		} else if (actor.canEat(other)) {
			actor.addEnergy(this.energyGained);
			other.setAlive(false);
		} else if (other.getType() == actor.getType()) {
			// mate?
		} else if (actor.getType() == ObjectType.HUNTER
				&& other.getType() == ObjectType.FOOD) {
			world[translateX(actor.getX() + dx)][translateY(actor.getY() + dy)] = actor;
			world[actor.getX()][actor.getY()] = other;
			int x = actor.getX();
			int y = actor.getY();
			actor.setX(translateX(actor.getX() + dx));
			actor.setY(translateY(actor.getY() + dy));
			other.setX(x);
			other.setY(y);

		}
	}

	public int translateX(int x) {

		if (x < 0) {
			return (this.width - 1);
		} else if (x >= this.width) {
			return 0;
		}

		return x;

	}

	public int translateY(int y) {

		if (y < 0) {
			return (this.height - 1);
		} else if (y >= this.height) {
			return 0;
		}

		return y;

	}

	public void simulateActor(Actor actor, Action action) {

		if (action == Action.RANDOM) {

			action = Action.getRandomMovement();

		}

		switch (action) {

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
			this.doAction(actor, 0, 0);
			break;
		}

	}

	public void randomizeList(ArrayList<Actor> actorList) {

		ArrayList<Actor> newList = new ArrayList<Actor>();
		int length = actorList.size();

		for (int i = 0; i < length; i++) {

			int random = rand.nextInt(actorList.size());

			Actor current = actorList.remove(random);
			newList.add(current);

		}

		this.actors = newList;
	}

	public void removeKilled(ArrayList<Actor> actorList) {

		ArrayList<Actor> newList = new ArrayList<Actor>();

		for (int i = 0; i < actorList.size(); i++) {

			if (actorList.get(i).isAlive()) {
				newList.add(actorList.get(i));
			} else {
				Actor a = actorList.get(i);
				if (a.getType() == ObjectType.PREY) {
					dead.offer(a.clone());
					preyCount--;
				}
				world[a.getX()][a.getY()] = new GameObject(a.getX(), a.getY(),
						ObjectType.NONE, false);
			}
		}

		this.actors = newList;

	}

	public void tick() {

		this.ticks++;

		this.randomizeList(this.actors);

		for (int i = 0; i < actors.size(); i++) {

			Actor current = actors.get(i);
			Perception percept = new Perception(current.getX(), current.getY(),
					world);
			Action a = current.getNextAction(percept);
			this.simulateActor(current, a);

		}

		this.removeKilled(this.actors);

		if (this.regenCounter > 0) {
			this.regenCounter = this.regenCounter - 1;
		} else {
			this.spawnNewFood();
			this.regenCounter = this.regenTurn;
			// System.out.println(dead.peek().getFitness());
			int num = 50;
			if (dead.size() < num) {
				num = dead.size();
			}
			PriorityQueue<Actor> deadStore = new PriorityQueue<Actor>();
			for (int i = 0; i < num; i++) {
				deadStore.add(dead.remove());
			}
			this.dead = deadStore;
		}
		if (this.preyCount == 0) {
			int num = 50;
			if (dead.size() < num) {
				num = dead.size();
			}
			int total = 0;
			ArrayList<Actor> newList = new ArrayList<Actor>();
			for (int i = 0; i < num; i++) {
				Actor a = dead.remove();
				newList.add(a);
				total += a.getFitness();
				reset(a);
			}
			System.out.println(total / num + " " + ticks);
			try {
				PrintStream out = new PrintStream(
						new AppendFileStream(this.myFile));
				out.println(total / num + " " + ticks);
				out.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}

			preyCount += num;
			this.ticks = 0;
			this.loadNextWorld(newList);
		}

	}

	public void reset(Actor a) {
		a.setEnergy(this.preyEnergy);
		a.setAlive(true);
		a.setFitness(0);
	}
}
