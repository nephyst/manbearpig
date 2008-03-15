package rules;

import java.util.Random;

import GameWorld.Perception;
import actors.ObjectType;


public class Rule {
	
	private Direction direction;
	private boolean has; //true has; false doesnt have;
	private ObjectType objectType;
	private Action action;
	
	
	public Rule () {
		generateRandomRule();
	}
	public Rule (Direction direction, boolean has, ObjectType objectType, Action action) {
		this.direction = direction;
		this.has = has;
		this.objectType = objectType;
		this.action = action;
	}
	
	public void generateRandomRule() {
		Random r = new Random();
		Direction directions[] = Direction.values();
		ObjectType objectTypes[] = ObjectType.values();
		Action actions[] = Action.values();
		
		this.direction = directions[r.nextInt(directions.length)];
		this.objectType = objectTypes[r.nextInt(objectTypes.length)];
		this.action = actions[r.nextInt(actions.length)];
		this.has = (r.nextInt(2) == 0);
	}
	
	public boolean checkRule(Perception p) {
		return (p.getDirection(direction).getType() == objectType) == has;
	}
	
	public Action getAction() {
		return this.action;
	}
	
	public Rule mutate() {
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
		return new Rule(newDir, newHas, newType, newAct);
	}
}
