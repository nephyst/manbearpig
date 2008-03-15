package rules;

import java.util.ArrayList;
import java.util.Random;

import GameWorld.Perception;
import actors.ObjectType;


public class Rules {
	
	private ArrayList<SimpleRule> up;
	private ArrayList<SimpleRule> down;
	private ArrayList<SimpleRule> left;
	private ArrayList<SimpleRule> right;
	private ArrayList<SimpleRule> random;
	
	public Rules () {
		up = new ArrayList<SimpleRule>();
		down = new ArrayList<SimpleRule>();
		left = new ArrayList<SimpleRule>();
		right = new ArrayList<SimpleRule>();
		random = new ArrayList<SimpleRule>();
		generateRandomRules();
	}
	
	public void generateRandomRules() {
		Random r = new Random();
		for (int i = 0; i < r.nextInt(10); i++) {
			up.add(new SimpleRule());
		}
		for (int i = 0; i < r.nextInt(10); i++) {
			down.add(new SimpleRule());
		}
		for (int i = 0; i < r.nextInt(10); i++) {
			left.add(new SimpleRule());
		}
		for (int i = 0; i < r.nextInt(10); i++) {
			right.add(new SimpleRule());
		}
		for (int i = 0; i < r.nextInt(10); i++) {
			random.add(new SimpleRule());
		}
	}
	
	public Action getAction(Perception p) {
		Action a = Action.NONE;
		int maxDesire = 0;
		int desire = 0;
		for (SimpleRule r : up) {
			desire += r.checkRule(p);
		}
		if (desire > maxDesire) {
			maxDesire = desire;
			a = Action.UP;
		}
		desire = 0;
		
		for (SimpleRule r : down) {
			desire += r.checkRule(p);
		}
		if (desire > maxDesire) {
			maxDesire = desire;
			a = Action.DOWN;
		}
		desire = 0;
		
		for (SimpleRule r : left) {
			desire += r.checkRule(p);
		}
		if (desire > maxDesire) {
			maxDesire = desire;
			a = Action.LEFT;
		}
		desire = 0;
		
		for (SimpleRule r : right) {
			desire += r.checkRule(p);
		}
		if (desire > maxDesire) {
			maxDesire = desire;
			a = Action.RIGHT;
		}
		desire = 0;
		
		for (SimpleRule r : random) {
			desire += r.checkRule(p);
		}
		if (desire > maxDesire) {
			a = Action.RANDOM;
		}
		return a;
	}
	
	/*public Rules mutate() {
		Random r = new Random();
		int i = r.nextInt(4);
		
		Direction directions[] = Direction.values();
		ObjectType objectTypes[] = ObjectType.values();
		Action actions[] = Action.values();
		
		Direction newDir = this.direction;
		boolean newHas = has;
		ObjectType newType = objectType;
		Action newAct = action;

		switch (i) {
		case 0:
			newDir = directions[r.nextInt(directions.length)];
			break;
		case 1:
			newType = objectTypes[r.nextInt(objectTypes.length)];
			break;
		case 2:
			newAct = actions[r.nextInt(actions.length)];
			break;
		case 3: 
			newHas = (r.nextInt(2) == 0);
			break;
		}
		return new Rules(newDir, newHas, newType, newAct);
	}*/
}
