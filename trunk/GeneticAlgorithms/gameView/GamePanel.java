package gameView;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import javax.swing.JPanel;

import GameWorld.GameWorld;
import actors.*;

public class GamePanel extends JPanel {

	private GameWorld world;

	private boolean grid = true;

	private Timer t;

	private int width;

	private int height;

	public GamePanel(int width, int height, GameWorld world, int ms) {

		this.world = world;
		t = new Timer(ms, new TickListener());
		this.setUp(width, height);
		this.width = width;
		this.height = height;
		t.start();

	}

	public void setUp(int width, int height) {

		this.setSize(new Dimension(width, height));

	}

	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		int blockWidth = this.getWidth() / world.getWidth();
		int blockHeight = this.getHeight() / world.getHeight();

		GameObject[][] objects = world.getWorld();

		for (int i = 0; i < world.getWidth(); i++) {
			for (int j = 0; j < world.getHeight(); j++) {

				GameObject current = objects[i][j];
				ObjectType type = current.getType();

				if (type != ObjectType.NONE) {

					Color c = Color.white;

					switch (type) {

					case NONE:
						c = Color.white;
						break;

					case FOOD:
						c = Color.green;
						break;

					case PREY:
						c = Color.blue;
						break;

					case HUNTER:
						c = Color.red;
						break;

					case WALL:
						c = Color.gray;
						break;
					}
					
					g2d.setColor(c);
					g2d.fillRect(current.getX() * blockWidth, current.getY()
							* blockHeight, blockWidth, blockHeight);
				}
			}
		}

		if (grid) {
			g2d.setColor(Color.black);
			int i = 0;

			while (i < this.getWidth()) {
				g2d.drawLine(i, 0, i, this.getHeight());
				i = i + blockWidth;
			}
			i = 0;

			while (i < this.getHeight()) {
				g2d.drawLine(0, i, this.getWidth(), i);
				i = i + blockHeight;
			}
		}

	}

	private class TickListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			world.tick();
			repaint();

		}

	}

}
