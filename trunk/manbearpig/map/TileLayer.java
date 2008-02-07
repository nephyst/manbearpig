package map;



public class TileLayer extends MapLayer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3962401219346004537L;
	/**
	 * 
	 */
	private int[][] tiles;
	public TileLayer(int width, int  height) {
		super(width, height);
		this.tiles = new int[width][height];
	}
	public int getTile(int x, int y) {
		return this.tiles[x][y];
	}
	public void setTile (int tile, int x, int y) {
		this.tiles[x][y] = tile;
	}
}
