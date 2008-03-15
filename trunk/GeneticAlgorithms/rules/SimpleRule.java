package rules;

import GameWorld.Perception;
import actors.Actor;
import actors.ObjectType;

public class SimpleRule {

	private Actor actor;

	private int desire;

	private Direction direction;

	public SimpleRule(Actor actor, int desire, Direction direction) {
		this.actor = actor;
		this.desire = desire;
		this.direction = direction;
	}
	public SimpleRule() {
		Direction directions[] = Direction.values();
		ObjectType objectTypes[] = ObjectType.values();
		Action actions[] = Action.values();
		
	}

	public int checkRule(Perception p) {
		if (p.getDirection(direction) == actor) {
			return desire;
		}
		return 0;
	}
}
