package rules;

import java.util.Random;

import GameWorld.Perception;
import actors.ObjectType;

public class SimpleRule {

	private ObjectType type;

	private int desire;

	private Direction direction;

	public SimpleRule(ObjectType type, int desire, Direction direction) {
		this.type = type;
		this.desire = desire;
		this.direction = direction;
	}
	public SimpleRule() {
		Random r = new Random();
		Direction directions[] = Direction.values();
		ObjectType objects[] = ObjectType.values();
		
		this.type = objects[r.nextInt(5)];
		this.desire = 1;//r.nextInt(2) + 1;
		this.direction = directions[r.nextInt(9)];
	}

	public int checkRule(Perception p) {
		if (p.getDirection(direction).getType() == type) {
			return desire;
		}
		return 0;
	}
}
