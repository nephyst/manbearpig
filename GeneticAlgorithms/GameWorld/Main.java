package GameWorld;
import gameView.GameFrame;
import java.util.HashMap;


public class Main {
 
	
	public static void main(String args[]){
		
		HashMap map = new HashMap<String,Integer>();
		
		map.put("width", 200);
		map.put("height", 150);
		
		map.put("ms", 20);
		
		map.put("regenRate",30);
		map.put("regenTurn", 10);
		
		map.put("preyEnergy", 250);
		map.put("hunterEnergy", 1);
		map.put("foodEnergy", 300);
		map.put("foodEnergy", 300);
		
		map.put("energyGained", 30);
		
		map.put("preyCount",500);
		map.put("foodCount", 100);
		map.put("hunterCount",30);
		map.put("rockCount", 10);
		
		GameFrame frame = new GameFrame(map);
		
	}
	
}
