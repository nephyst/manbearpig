package map;



public class WalkableLayer extends MapLayer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8754111723038648054L;
	/**
	 * 
	 */
	private boolean[][] walkable;
	public WalkableLayer(int width, int  height) {
		super(width, height);
		this.walkable = new boolean[width][height];
	}
	public boolean isWalkable(int x, int y) {
		return this.walkable[x][y];
	}
	public void setWalkable (boolean walkable, int x, int y) {
		this.walkable[x][y] = walkable;
	}
	public void toggleWalkable(int x, int y) {
		this.walkable[x][y] = !this.walkable[x][y];
	}
}
