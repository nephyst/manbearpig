package map;

import java.awt.Component;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Map {

	private int height;
	private int width;
	
	private WalkableLayer walkable;
	
	private double scale;
	
	private ArrayList<TileLayer> layers;
	private ArrayList<BufferedImage> images;
	private ArrayList<AnimatedTile> anim;
	private ArrayList<Image> manages;

	public Map(int width, int height, ArrayList<TileLayer> layers,
			WalkableLayer walkable, ArrayList<BufferedImage> images,
			ArrayList<AnimatedTile> anim) {
		this.width = width;
		this.height = height;

		this.layers = layers;
		this.walkable = walkable;
		this.images = images;
		this.anim = anim;

		this.scale = 1.0;
		this.manages = new ArrayList<Image>();
	}
	
	public Image getImage(int layer, int x, int y, byte frame) {
		if (x >= this.width || y >= this.height) {
			return null;
		}
		TileLayer l = layers.get(layer);
		int tileReference = l.getTile(x, y);
		if (tileReference == 0) {
			return null;
		} else if (tileReference < 0) {
			return (this.manages.get(anim.get(Math.abs(tileReference)).getFrame(frame)));
		} else {
			return (this.manages.get(tileReference));
		}
	}
	public Image getTranslatedImage(int layer, double x, double y, byte frame) {
		int size = (int)(32 * scale);
		return this.getImage(layer, (int)x/size, (int)y/size, frame);
	}
	public void setupManages(Component gui, double scale) {
		this.scale = scale;
		this.manages.clear();

		AffineTransformOp op = new AffineTransformOp(AffineTransform
				.getScaleInstance(scale, scale), null);

		for (BufferedImage bi : this.images) {

			bi = op.filter(bi, null);
			Image managed = gui.getGraphicsConfiguration()
					.createCompatibleImage(bi.getWidth(), bi.getHeight(),
							Transparency.BITMASK);
			managed.getGraphics().drawImage(bi, 0, 0, gui);

			this.manages.add(managed);
		}
	}

	public double getScale() {
		return this.scale;
	}
	
	public int getTileHeight() {
		return this.height;
	}

	public int getNumLayers() {
		return this.layers.size();
	}

	public int getPixelHeight() {
		return this.getTileHeight() * 32;
	}

	public int getPixelWidth() {
		return this.getTileWidth() * 32;
	}

	public int getTileWidth() {
		return width;
	}

	public boolean isWalkable(int x, int y) {
		return this.walkable.isWalkable(x, y);
	}
}
