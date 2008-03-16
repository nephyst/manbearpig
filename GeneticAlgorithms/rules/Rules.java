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

	public Rules() {
		up = new ArrayList<SimpleRule>();
		down = new ArrayList<SimpleRule>();
		left = new ArrayList<SimpleRule>();
		right = new ArrayList<SimpleRule>();
		generateRandomRules();
	}
	
	public Rules(ArrayList<SimpleRule>[] rules) { 
		this.up = rules[0];
		this.down = rules[1];
		this.left = rules[2];
		this.right = rules[3];
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
	}

	public Action getAction(Perception p) {
		int desireUp = 0;
		int desireDown = 0;
		int desireLeft = 0;
		int desireRight = 0;
		
		for (SimpleRule r : up)
			desireUp += r.checkRule(p);
		
		for (SimpleRule r : down)
			desireDown += r.checkRule(p);

		for (SimpleRule r : left)
			desireLeft += r.checkRule(p);
		
		for (SimpleRule r : right)
			desireRight += r.checkRule(p);
		
		int maxDesire = Math.max(desireUp, desireDown);
		maxDesire = Math.max(maxDesire, desireLeft);
		maxDesire = Math.max(maxDesire, desireRight);
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
		Random r = new Random();
		return action.get(r.nextInt(action.size()));
	}
	
	public static Rules best() {
		ArrayList<SimpleRule> upClone = new ArrayList<SimpleRule>();
		ArrayList<SimpleRule> downClone = new ArrayList<SimpleRule>();
		ArrayList<SimpleRule> leftClone = new ArrayList<SimpleRule>();
		ArrayList<SimpleRule> rightClone = new ArrayList<SimpleRule>();
		
		upClone.add(new SimpleRule(ObjectType.FOOD, Direction.UP));
		downClone.add(new SimpleRule(ObjectType.FOOD, Direction.DOWN));
		leftClone.add(new SimpleRule(ObjectType.FOOD, Direction.LEFT));
		rightClone.add(new SimpleRule(ObjectType.FOOD, Direction.RIGHT));
		
		upClone.add(new SimpleRule(ObjectType.HUNTER, Direction.DOWN));
		downClone.add(new SimpleRule(ObjectType.HUNTER, Direction.UP));
		leftClone.add(new SimpleRule(ObjectType.HUNTER, Direction.RIGHT));
		rightClone.add(new SimpleRule(ObjectType.HUNTER, Direction.LEFT));
		
		upClone.add(new SimpleRule(ObjectType.HUNTER, Direction.DOWN));
		downClone.add(new SimpleRule(ObjectType.HUNTER, Direction.UP));
		leftClone.add(new SimpleRule(ObjectType.HUNTER, Direction.RIGHT));
		rightClone.add(new SimpleRule(ObjectType.HUNTER, Direction.LEFT));
		
		upClone.add(new SimpleRule(ObjectType.WALL, Direction.DOWN));
		downClone.add(new SimpleRule(ObjectType.WALL, Direction.UP));
		leftClone.add(new SimpleRule(ObjectType.WALL, Direction.RIGHT));
		rightClone.add(new SimpleRule(ObjectType.WALL, Direction.LEFT));
		
		ArrayList<SimpleRule> ruleList[] = (ArrayList<SimpleRule>[])new ArrayList[4];
		ruleList[0] = upClone;
		ruleList[1] = downClone;
		ruleList[2] = leftClone;
		ruleList[3] = rightClone;
		Rules rlz = new Rules(ruleList);
		return rlz;
	}
	
	public static Rules random() {
		return new Rules();
	}
	
	public Rules mutate() {
		ArrayList<SimpleRule> upClone = new ArrayList<SimpleRule>();
		ArrayList<SimpleRule> downClone = new ArrayList<SimpleRule>();
		ArrayList<SimpleRule> leftClone = new ArrayList<SimpleRule>();
		ArrayList<SimpleRule> rightClone = new ArrayList<SimpleRule>();
		
		for (SimpleRule r : up) {
			upClone.add(r.clone());
		}
		for (SimpleRule r : down) {
			downClone.add(r.clone());
		}
		for (SimpleRule r : left) {
			leftClone.add(r.clone());
		}
		for (SimpleRule r : right) {
			rightClone.add(r.clone());
		}
		
		Random r = new Random();
		int i = r.nextInt(4);
		int j = r.nextInt(4);
		switch (i) {
		case 0:
			upClone.remove(j);
			upClone.add(new SimpleRule());
			break;
		case 1:
			downClone.remove(j);
			downClone.add(new SimpleRule());
			break;
			
		case 2:
			leftClone.remove(j);
			leftClone.add(new SimpleRule());
			break;
			
		case 3:
			rightClone.remove(j);
			rightClone.add(new SimpleRule());
			break;
		}
		
		ArrayList<SimpleRule> ruleList[] = (ArrayList<SimpleRule>[])new ArrayList[5];
		ruleList[0] = upClone;
		ruleList[1] = downClone;
		ruleList[2] = leftClone;
		ruleList[3] = rightClone;
		Rules rlz = new Rules(ruleList);
		return rlz;
	}
}
