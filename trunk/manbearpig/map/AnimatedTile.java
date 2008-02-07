package map;

import java.util.ArrayList;

public class AnimatedTile {
	
	private ArrayList<Byte> tiles;
	
	public AnimatedTile(byte b) {
		this.tiles = new ArrayList<Byte>();
		this.tiles.add(b);
	}
	public AnimatedTile(byte[] bytes) {
		tiles = new ArrayList<Byte>();
		for (byte b : bytes) {
			tiles.add(b);
		}
	}
	
	public void addFrame(byte b) {
		this.tiles.add(b);
	}
	public byte getFrame(byte b) {
		return tiles.get(b % tiles.size());
	}
	
	public ArrayList<Byte> getFrames() {
		return tiles;
	}
	
	public int getSize() {
		return tiles.size();
	}
}
