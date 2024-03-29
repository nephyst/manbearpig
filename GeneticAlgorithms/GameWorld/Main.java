package GameWorld;
import gameView.GameFrame;
import java.util.HashMap;

public class Main {
	
	public static void main(String args[]){
		
HashMap<String,Integer> map = new HashMap<String,Integer>();
		
		//Number of cells in the game world
		map.put("width", 200); //Width
		map.put("height", 100); //Height
		
		//Size of the window (Resolution)
		map.put("windowWidth",1200); //Width
		map.put("windowHeight",600); //Height

		//Tick rate of the clock
		map.put("ms", 1);
		
		//Regeneration control for the food
		map.put("regenRate",10); //Chance that food will be spawned
		map.put("regenTurn", 3); //How many turns until food is spawned
		
		//Adjusts how long actors will live
		map.put("preyEnergy", 200); //How long prey will live
		map.put("hunterEnergy", 1); //How log hunters will live
		map.put("foodEnergy", 300); //How long food will live
		
		//When eating, how much energy is gained
		map.put("energyGained", 32); 
		
		//Values used for building the initial world
		//(N / 10000) chance for a object to be spawned
		map.put("preyCount",50); //Number of prey
		map.put("foodCount", 300); //Number of food
		map.put("hunterCount",25); //number of hunters
		map.put("rockCount", 80); //number of rocks
		
		//When an actor's energy exceeds this level,  
		map.put("respawnLevel",350);
		
		GameFrame frame = new GameFrame(map);
		
	}
}
