package rules;
import java.util.Random;

public enum Action {

	LEFT, RIGHT, UP, DOWN, NONE, RANDOM;
	
	public static Action getRandomMovement() {
		
		double rand = Math.random();
		
		if (rand < .25){
			return LEFT;
		}else if (rand >= .25 && rand <.50){
			return RIGHT;
		}else if (rand >= .50 && rand < .75){
			return DOWN;
		}else {
			return UP;
		}
	}
	
	public static Action getRandomFromList(Action[] list){
		
		Random rand = new Random();
		int random = rand.nextInt(list.length);
		return list[random];
	}
	
	
	
}
