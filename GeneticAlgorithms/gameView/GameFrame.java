package gameView;


import javax.swing.JFrame;

import GameWorld.GameWorld;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;

public class GameFrame extends JFrame{

	private GameWorld world;
	
	private int windowWidth = 1024;
	private int windowHeight = 768;
	private final int ms;
	
	private Toolkit kit = Toolkit.getDefaultToolkit();
	
	private GamePanel gamePanel;
	
	public GameFrame(HashMap<String,Integer> map){
		
		this.ms = map.get("ms");
		this.windowWidth = map.get("windowWidth");
		this.windowHeight = map.get("windowHeight");
		
		Dimension d = this.getCorrectDimension(map.get("width"), map.get("height"));
		
		this.setUpWorld(map);
		this.addComponents(d);
		this.setUp(d);
		this.startup();
		
	}
	
	public void setUpWorld(HashMap<String,Integer> map){
		
		//world = new GameWorld(map.get("width"),map.get("height"));
		world = new GameWorld(map);
		
	}
	
	public Dimension getCorrectDimension(int gameWidth, int gameHeight){
		
		int width = (windowWidth / gameWidth) * gameWidth;
		int height = (windowHeight / gameHeight) * gameHeight;
		
		return new Dimension(width,height);
		
	}
	
	public void setUp(Dimension d){
		
		int screenWidth = kit.getScreenSize().width;
		int screenHeight = kit.getScreenSize().height;
		
		this.setSize(d.width+7,d.height+33);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Genetic Algorithms");
		this.setLocation((screenWidth/2) - (d.width/2),(screenHeight/2) - (d.height/2));
		this.setLayout(null);
		
	}
	
	public void addComponents(Dimension d){
		

		this.gamePanel = new GamePanel(d.width,d.height,world,ms);
		this.add(gamePanel);
	
	}
	
	public void startup(){
		
		this.setVisible(true);
		
	}
	
}
