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

	public Rules() {
		up = new ArrayList<SimpleRule>();
		down = new ArrayList<SimpleRule>();
		left = new ArrayList<SimpleRule>();
		right = new ArrayList<SimpleRule>();
		random = new ArrayList<SimpleRule>();
		generateRandomRules();
	}

	public void generateRandomRules() {
		for (int i = 0; i < 4; i++) {
			up.add(new SimpleRule());
		}
		for (int i = 0; i < 4; i++) {
			down.add(new SimpleRule());
		}
		for (int i = 0; i < 4; i++) {
			left.add(new SimpleRule());
		}
		for (int i = 0; i < 4; i++) {
			right.add(new SimpleRule());
		}
		for (int i = 0; i < 6; i++) {
			random.add(new SimpleRule());
		}
	}

	public Action getAction(Perception p) {
		int desireUp = 0;
		int desireDown = 0;
		int desireLeft = 0;
		int desireRight = 0;
		int desireRandom = 0;
		
		for (SimpleRule r : up)
			desireUp += r.checkRule(p);
		
		for (SimpleRule r : down)
			desireDown += r.checkRule(p);

		for (SimpleRule r : left)
			desireLeft += r.checkRule(p);
		
		for (SimpleRule r : right)
			desireRight += r.checkRule(p);
		
		for (SimpleRule r : random)
			desireRandom += r.checkRule(p);
		
		int maxDesire = Math.max(desireUp, desireDown);
		maxDesire = Math.max(maxDesire, desireLeft);
		maxDesire = Math.max(maxDesire, desireRight);
		maxDesire = Math.max(maxDesire, desireRandom);
		ArrayList<Action>  action = new ArrayList<Action>();
		if (desireUp == maxDesire) {
			action.add(Action.UP);
		}
		if (desireDown == maxDesire) {
			action.add(Action.DOWN);
		}
		if (desireLeft == maxDesire) {
			action.add(Action.LEFT);
		}
		if (desireRight == maxDesire) {
		
			action.add(Action.RIGHT);
		}
		if (desireRandom == maxDesire) {
			action.add(Action.RANDOM);
		}
		Random r = new Random();
		return action.get(r.nextInt(action.size()));
	}

	/*
	 * public Rules mutate() { Random r = new Random(); int i = r.nextInt(4);
	 * 
	 * Direction directions[] = Direction.values(); ObjectType objectTypes[] =
	 * ObjectType.values(); Action actions[] = Action.values();
	 * 
	 * Direction newDir = this.direction; boolean newHas = has; ObjectType
	 * newType = objectType; Action newAct = action;
	 * 
	 * switch (i) { case 0: newDir = directions[r.nextInt(directions.length)];
	 * break; case 1: newType = objectTypes[r.nextInt(objectTypes.length)];
	 * break; case 2: newAct = actions[r.nextInt(actions.length)]; break; case
	 * 3: newHas = (r.nextInt(2) == 0); break; } return new Rules(newDir,
	 * newHas, newType, newAct); }
	 */
}
