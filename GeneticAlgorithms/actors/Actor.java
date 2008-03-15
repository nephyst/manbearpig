package actors;

import java.util.ArrayList;

import rules.Action;
import rules.Rules;
import GameWorld.Perception;

public class Actor extends GameObject {

	private int energy;

	private int life;
	
	private Rules rules;
	
	public Actor(int x, int y, ObjectType type, boolean isAlive) {
		super(x, y, type, isAlive);
		if (type == ObjectType.PREY)
			this.rules = new Rules();

	}

	public Actor(int x, int y, ObjectType type, boolean isAlive,
			Rules rules) {
		super(x, y, type, isAlive);
		this.rules = rules;
	}

	public Action getNextAction(Perception percept) {
		if (this.getType() == ObjectType.PREY) {
			return rules.getAction(percept);
		} else if (this.getType() == ObjectType.HUNTER) {
			return Action.RANDOM;
		}
		return Action.NONE;
	}

//	public Actor mutate(Actor other) {
//		ArrayList<Rules> newRule = new ArrayList<Rules>();
//		for (int i = 0; i < 4; i++) {
//			int r = (int) (Math.random() * 2);
//			if (r == 1) {
//			//	newRule.add(this.getRule(i));
//			} else {
//			//	newRule.add(other.getRule(i));
//			}
//		}
//		int r = (int) (Math.random() * 4);
//		//newRule.add(newRule.get(r).mutate());
//		//newRule.remove(r);
//		//return new Actor(this.getX(), this.getY(), this.getType(), true,
//			//	newRule);
//	}

	public boolean canEat(GameObject other) {
		boolean ret = true;
		if (!other.isAlive()) {
			//ret = false;
		} else if (this.getType() == ObjectType.PREY &&
				   other.getType() != ObjectType.FOOD) {
			ret = false;
		} else if (this.getType() == ObjectType.HUNTER &&
					other.getType() != ObjectType.PREY) {
			ret = false;
		}

		return ret;
	}
}
