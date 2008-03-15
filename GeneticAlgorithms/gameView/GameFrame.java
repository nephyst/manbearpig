package gameView;


import javax.swing.JFrame;

import GameWorld.GameWorld;

import java.awt.Dimension;
import java.awt.Toolkit;

public class GameFrame extends JFrame{

	private GameWorld world;
	
	private final int windowWidth = 1024;
	private final int windowHeight = 768;
	private final int ms;
	
	private Toolkit kit = Toolkit.getDefaultToolkit();
	
	private GamePanel gamePanel;
	
	public GameFrame(int gameWidth,int gameHeight, int ms){
		
		Dimension d = this.getCorrectDimension(gameWidth, gameHeight);
		
		this.ms = ms;
		
		this.setUpWorld(gameWidth, gameHeight);
		this.addComponents(d);
		this.setUp(d);
		this.startup();
		
	}
	
	public void setUpWorld(int width, int height){
		
		world = new GameWorld(width,height);
		
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
