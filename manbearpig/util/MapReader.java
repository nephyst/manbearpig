package util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import map.AnimatedTile;
import map.Map;
import map.TileLayer;
import map.WalkableLayer;

public abstract class MapReader {

	public static Map readFile(String filename) throws Exception {

		ArrayList<TileLayer> layers = new ArrayList<TileLayer>();
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		ArrayList<AnimatedTile> anim = new ArrayList<AnimatedTile>();
		System.out.println("Reading File: " + "resources/" + filename);
		Scanner scanner = new Scanner(new File("resources/" + filename));
		int width = Integer.parseInt(scanner.nextLine());
		int height = Integer.parseInt(scanner.nextLine());

		String s = scanner.nextLine();
		if (!s.startsWith("!A")) {
			System.out.println("Invalid Format");
		}
		s = scanner.nextLine();

		while (!s.startsWith("!B")) {
			byte[] bytes = Base64.decode(s.toCharArray());
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			BufferedImage im = ImageIO.read(bais);
			images.add(im);
			s = scanner.nextLine();
		}

		s = scanner.nextLine();

		while (!s.startsWith("!C")) {
			byte[] bytesIn = Base64.decode(s.toCharArray());
			anim.add(new AnimatedTile(bytesIn));
			s = scanner.nextLine();
		}

		int size = width * height;
		s = scanner.nextLine();

		boolean[] bitsOut = Base64.decodeBoolean(s.toCharArray());
		WalkableLayer walkable = new WalkableLayer(width, height);
		for (int i = 0; i < size; i++) {
			walkable.setWalkable(bitsOut[i], i % width, i / width);
		}
		while (scanner.hasNextLine()) {
			byte[] bytesOut = Base64.decode(scanner.nextLine().toCharArray());
			TileLayer tl = new TileLayer(width, height);
			for (int i = 0; i < size; i++) {
				tl.setTile(bytesOut[i], i % width, i / width);
			}
			layers.add(tl);
		}

		return new Map(width, height, layers, walkable, images, anim);
	}
}
