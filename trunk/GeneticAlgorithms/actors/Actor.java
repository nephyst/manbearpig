package actors;

import rules.Action;
import rules.Rules;
import GameWorld.Perception;

public class Actor extends GameObject {

	private int energy;

	private Rules rules;
	
	private boolean spawn = false;

	public Actor(int x, int y, ObjectType type, boolean isAlive, int energy) {
		super(x, y, type, isAlive);
		this.energy = energy;
		if (type == ObjectType.PREY)
			this.rules = new Rules();

	}
	
	public boolean isSpawn(){
		return this.spawn;
	}

	public Actor(int x, int y, ObjectType type, boolean isAlive, Rules rules) {
		super(x, y, type, isAlive);
		this.rules = rules;
		this.spawn = true;
	}

	public void addEnergy(int eng) {
		this.energy = this.energy + eng;
	}

	public void subtractEnergy(int eng) {
		this.energy = this.energy - eng;
	}

	public int getEnergy() {

		return this.energy;

	}

	public Action getNextAction(Perception percept) {

		if (this.energy <= 0) {
			this.setAlive(false);
		} else {

			if (this.getType() == ObjectType.PREY) {
				return rules.getAction(percept);
			} else if (this.getType() == ObjectType.HUNTER) {
				return Action.RANDOM;
			}
		}
		return Action.NONE;
	}

	public Actor spawn() {
		Rules r = this.rules.mutate();
		return new Actor(this.getX(), this.getY(), this.getType(), true, r);
	}
	public void setEnergy(int amount) {
		this.energy = amount;
	}
	
	// public Actor mutate(Actor other) {
	// ArrayList<Rules> newRule = new ArrayList<Rules>();
	// for (int i = 0; i < 4; i++) {
	// int r = (int) (Math.random() * 2);
	// if (r == 1) {
	// // newRule.add(this.getRule(i));
	// } else {
	// // newRule.add(other.getRule(i));
	// }
	// }
	// int r = (int) (Math.random() * 4);
	// //newRule.add(newRule.get(r).mutate());
	// //newRule.remove(r);
	// //return new Actor(this.getX(), this.getY(), this.getType(), true,
	// // newRule);
	// }

	public boolean canEat(GameObject other) {

		boolean ret = false;
		if (this.getType() == ObjectType.PREY
				&& other.getType() == ObjectType.FOOD &&
				other.isAlive()) {
			ret = true;
		} else if (this.getType() == ObjectType.HUNTER &&
					other.getType() == ObjectType.PREY &&
					other.isAlive()) {
			ret = true;
		}

		return ret;
	}
}
