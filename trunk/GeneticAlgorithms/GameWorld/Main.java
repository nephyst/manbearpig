package GameWorld;
import gameView.GameFrame;
import java.util.HashMap;


public class Main {
 
	
	public static void main(String args[]){
		
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		
		map.put("width", 40);
		map.put("height", 30);
		
		map.put("windowWidth",1024);
		map.put("windowHeight",768);

		map.put("ms", 800);
		
		map.put("regenRate",30);
		map.put("regenTurn", 1);
		
		map.put("preyEnergy", 250);
		map.put("hunterEnergy", 1);
		map.put("foodEnergy", 300);
		
		map.put("energyGained", 200);
		
		map.put("preyCount",100);
		map.put("foodCount", 300);
		map.put("hunterCount",00);
		map.put("rockCount", 10);
		
		GameFrame frame = new GameFrame(map);
		
	}
	
}
