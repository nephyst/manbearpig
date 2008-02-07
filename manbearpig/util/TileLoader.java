package util;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TileLoader {

	private static GraphicsConfiguration gc;

	private static ArrayList<BufferedImage> loadImage(File imageFile,
			int tileSize, int numTiles) {
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

		System.out.println("Reading File: " + imageFile.getPath());
		BufferedImage im;
		ArrayList<BufferedImage> strip = null;

		try {
			if (!imageFile.exists()) {
				throw new FileNotFoundException();
			}
			im = ImageIO.read(imageFile);
			int transparency = im.getColorModel().getTransparency();
			BufferedImage copy = gc.createCompatibleImage(im.getWidth(), im
					.getHeight(), transparency);
			
			Graphics2D g2d = copy.createGraphics();
			g2d.drawImage(im, 0, 0, null);
			g2d.dispose();

			int imHeight = im.getHeight();
			int imWidth = im.getWidth();

			int nWide = imWidth / tileSize;
			int nTall = imHeight / tileSize;

			strip = new ArrayList<BufferedImage>();
			Graphics2D stripGC;

			int image = 0;
			for (int i = 0; i < nTall; i++) {
				for (int j = 0; j < nWide; j++) {
					if ((numTiles == -1) || (image < numTiles)) {
						strip.add(gc.createCompatibleImage(tileSize, tileSize,
								transparency));
						stripGC = strip.get(image).createGraphics();
						stripGC.drawImage(copy, 0, 0, tileSize, tileSize, j
								* tileSize, i * tileSize, (j * tileSize)
								+ tileSize, (i * tileSize) + tileSize, null);
						stripGC.dispose();
						image++;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error reading file: " + imageFile.getPath());
		}
		return strip;
	}

	public static ArrayList<BufferedImage> loadImage(String str, int tileSize) {
		return loadImage(new File("resources/" + str), tileSize, -1);
	}

	public static ArrayList<BufferedImage> loadImage(String str, int tileSize,
			int numTiles) {
		return loadImage(new File("resources/" + str), tileSize, numTiles);
	}
	public static ArrayList<BufferedImage> loadImage(File file, int tileSize) {
		return loadImage("resources/" + file, tileSize, -1);
	}
}
