package data;

import java.awt.Component;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Images {

	private Image[][] images = new Image[4][3];
	private double scale = 1.0;
	
	public Images(ArrayList<BufferedImage> images, Component gui) {
		if (gui == null || images == null || images.size() != 12) {
			throw new IllegalArgumentException();
		}

		AffineTransformOp op = new AffineTransformOp(AffineTransform
				 .getScaleInstance(scale, scale), null);

		;
		for (Part p : Part.values()) {
			for (Animal a : Animal.values()) {
				BufferedImage bi = images.get(a.getAnimal() + p.getPart() * 4);
				BufferedImage temp = op.filter(bi, null);
				Image manage = gui.getGraphicsConfiguration()
						.createCompatibleImage(temp.getWidth(),
								temp.getHeight(), Transparency.BITMASK);
				manage.getGraphics().drawImage(temp, 0, 0, gui);
				this.images[a.getAnimal()][p.getPart()] = manage;
			}
		}
	}

	public Image get(Animal a, Part p) {
		return images[a.getAnimal()][p.getPart()];
	}
}