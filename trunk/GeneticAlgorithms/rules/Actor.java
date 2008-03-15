package rules;

import java.util.ArrayList;


import actors.GameObject;
import actors.ObjectType;

public class Actor extends GameObject {

	private int energy;

	private int life;

	private ArrayList<Rule> rules;

	public Actor(int x, int y, ObjectType type, boolean isAlive) {
		super(x, y, type, isAlive);

		this.rules = new ArrayList<Rule>();
		rules.add(new Rule()); // generate random rules
	}

	public Actor(int x, int y, ObjectType type, boolean isAlive,
			ArrayList<Rule> rules) {
		super(x, y, type, isAlive);
		this.rules = rules;
	}

	public Action getNextAction(Perception percept) {

		if (this.isAlive() && this.getType() != ObjectType.FOOD) {
			for (Rule r : this.rules) {
				if (r.checkRule(percept)) {
					return r.getAction();
				}
			}
		}
		return Action.NONE;

	}

	public Rule getRule(int i) {
		return this.rules.get(i);
	}

	public Actor mutate(Actor other) {
		ArrayList<Rule> newRule = new ArrayList<Rule>();
		for (int i = 0; i < 4; i++) {
			int r = (int)(Math.random() * 2);
			if (r == 1){
				newRule.add(this.getRule(i));
			} else {
				newRule.add(other.getRule(i));
			}
		}
		int r = (int)(Math.random() * 4);
		newRule.add(newRule.get(r).mutate());
		newRule.remove(r);
		return new Actor(this.getX(), this.getY(),this.getType(), true, newRule);
	}
}
