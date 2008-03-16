package rules;

import java.util.Random;

import GameWorld.Perception;
import actors.ObjectType;

public class SimpleRule {

	private ObjectType type;

	private Direction direction;

	public SimpleRule(ObjectType type, Direction direction) {
		this.type = type;
		this.direction = direction;
	}

	public SimpleRule() {
		Random r = new Random();
		Direction directions[] = Direction.values();
		ObjectType objects[] = ObjectType.values();

		this.type = objects[r.nextInt(5)];
		this.direction = directions[r.nextInt(9)];
	}

	public int checkRule(Perception p) {
		if (p.getDirection(direction).getType() == type) {
			return 1;
		}
		return 0;
	}

	public SimpleRule clone() {
		return new SimpleRule(type, direction);
	}
}
