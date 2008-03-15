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
	
	public Rules(ArrayList<SimpleRule>[] rules) { 
		this.up = rules[0];
		this.down = rules[1];
		this.left = rules[2];
		this.right = rules[3];
		this.random = rules[4];
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
		for (int i = 0; i < 4; i++) {
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
	
	public Rules mutate() {
		
		ArrayList<SimpleRule> upClone = new ArrayList<SimpleRule>();
		ArrayList<SimpleRule> downClone = new ArrayList<SimpleRule>();
		ArrayList<SimpleRule> leftClone = new ArrayList<SimpleRule>();
		ArrayList<SimpleRule> rightClone = new ArrayList<SimpleRule>();
		ArrayList<SimpleRule> randomClone = new ArrayList<SimpleRule>();
		
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
		for (SimpleRule r : random) {
			randomClone.add(r.clone());
		}
		
		Random r = new Random();
		int i = r.nextInt(5);
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
			
		case 4:
			randomClone.remove(j);
			randomClone.add(new SimpleRule());
			break;
		}
		
		ArrayList<SimpleRule> ruleList[] = (ArrayList<SimpleRule>[])new ArrayList[5];
		ruleList[0] = upClone;
		ruleList[1] = downClone;
		ruleList[2] = leftClone;
		ruleList[3] = rightClone;
		ruleList[4] = randomClone;
		return new Rules(ruleList);
	}
}
