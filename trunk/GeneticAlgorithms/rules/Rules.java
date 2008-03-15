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
	private ArrayList<SimpleRule> none;
	
	public Rules () {
		up = new ArrayList<SimpleRule>();
		down = new ArrayList<SimpleRule>();
		left = new ArrayList<SimpleRule>();
		right = new ArrayList<SimpleRule>();
		none = new ArrayList<SimpleRule>();
		generateRandomRules();
	}
	
	public void generateRandomRules() {
		Random r = new Random();
		Direction directions[] = Direction.values();
		ObjectType objectTypes[] = ObjectType.values();
		Action actions[] = Action.values();
	}
	
	public boolean checkRule(Perception p) {
		return (p.getDirection(direction).getType() == objectType) == has;
	}
	
	public Action getAction() {
		return this.action;
	}
	
	public Rules mutate() {
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
	}
}
