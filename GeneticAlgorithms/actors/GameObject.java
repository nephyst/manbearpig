package actors;

 
public class GameObject {

	private ObjectType type;
	
	private boolean alive;
	private int x;
	private int y;
	
	public GameObject (int x, int y,ObjectType type, boolean isAlive){
		this.alive = isAlive;
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public ObjectType getType(){
		return this.type;
	}
	
	public void setType(ObjectType type){
		this.type = type;
		
	}
	
	public void setAlive(boolean alive){
		this.alive = alive;
	}
	
	public boolean isAlive(){
		return this.alive;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
}
