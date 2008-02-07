package core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import map.Map;
import util.MapReader;
import util.TileLoader;
import actors.Actor;
import data.Images;
import data.Part;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener,
		MouseMotionListener {

	private final int WIDTH;

	private final int HEIGHT;

	private Graphics g;

	private Image dbImage = null;

	private Thread animator;

	private boolean running;

	private final long maxFps = 80;

	private final long period = (1000 / this.maxFps) * 1000000;

	private ArrayList<Long> frames = new ArrayList<Long>();

	private ArrayList<Long> updates = new ArrayList<Long>();

	private double mapOffsetX = 0;

	private double mapOffsetY = 0;

	private Map map;

	private boolean setup = false;

	private double scale = 1.0;

	private int tileSize = (int)(32 * scale);

	private boolean upKey = false;

	private boolean downKey = false;

	private boolean leftKey = false;

	private boolean rightKey = false;

	private int scrollValue = 7;
	
	private Point mouse = new Point(0,0);
	
	private Images images;
	
	private Actor actor;

	public GamePanel(int width, int height) {

		this.setFocusable(true);
		this.requestFocus();
		this.WIDTH = width;
		this.HEIGHT = height;

		System.setProperty("sun.java2d.reanslaccel", "true");
		System.setProperty("sun.java2d.ddforcevram", "true");
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(width, height));

		this.addMouseMotionListener(this);
		this.addKeyListener(this);

		try {
			this.map = MapReader.readFile("map1");
		} catch (Exception e) {
			System.out.println("Error: your map sucks");
			e.printStackTrace();
		}
		
		actor = new Actor();
		
		this.setVisible(true);
	}
	public void addNotify()
	// wait for JPanel to be added before we start
	{
		super.addNotify();
		images = new Images(TileLoader.loadImage("animal.png", 32), this);	
	}
	public void gameRender() {

		if (!setup) {
			map.setupManages(this, scale);
		}

		if (dbImage == null) {
			dbImage = createImage(this.WIDTH, this.HEIGHT);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			} else {
				g = dbImage.getGraphics();
			}
		}

		g.setColor(Color.blue);
		g.fillRect(0, 0, this.WIDTH, this.HEIGHT);

		int x0 = (int) mapOffsetX / tileSize;
		int y0 = (int) mapOffsetY / tileSize;
		if (x0 < 0)
			x0 = 0;
		if (y0 < 0)
			y0 = 0;
		int x1 = x0 + 1 + this.WIDTH / tileSize;
		int y1 = y0 + 2 + this.HEIGHT / tileSize;
		for (int h = 0; h < map.getNumLayers(); h++) {
			for (int i = x0; i < x1; i++) {
				for (int j = y0; j < y1; j++) {
					Image image = map.getImage(h, i, j, (byte) 0);
					if (image != null) {
						g.drawImage(image, (i * tileSize) - (int) mapOffsetX,
								(j * tileSize) - (int) mapOffsetY, this);
					}

				}
			}
		}

		g.setColor(Color.red);
		g.drawString("Frames: " + (double) this.frames.size() / 2.0, 10, 20);
		g.drawString("Updates: " + (double) this.updates.size() / 2.0, 10, 30);
		int x = (int)(mouse.x + mapOffsetX) / tileSize;
		int y = (int)(mouse.y + mapOffsetY) / tileSize;
		g.drawString("(" + x + "," + y + ")", mouse.x, mouse.y);
		
		for (Part p : Part.values()) {
			Image image = images.get(actor.getImage(p), p);
			g.drawImage(image, (10 * tileSize) - (int) mapOffsetX,
					(10 * tileSize) - (int) mapOffsetY, this);
		}
		
	}

	private void paintScreen() {
		this.frames.add(System.nanoTime());
		while (frames.get(0).longValue() < System.nanoTime() - 2000000000L) {
			frames.remove(0);
		}
		Graphics g;
		try {
			g = this.getGraphics();
			if ((g != null) && (dbImage != null))
				g.drawImage(dbImage, 0, 0, null);

			Toolkit.getDefaultToolkit().sync(); // sync display
			g.dispose();
			
		} catch (Exception e) {
			System.out.println("Graphics context error: " + e);
		}
	}

	public void startGame() {
		if (animator == null || !running) {
			animator = new Thread(this);
			animator.start();
		}
	}

	public void run() {
		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;
		long excess = 0L;
		int maxFrameSkips = 5;
		int noDelaysPerYield = 5;

		beforeTime = System.nanoTime();

		running = true;
		while (running) {
			gameUpdate();
			gameRender();
			paintScreen();

			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;

			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime / 1000000L);
				} catch (Exception e) {
				}
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			} else {
				excess -= sleepTime;
				overSleepTime = 0L;

				if (++noDelays >= noDelaysPerYield) {
					Thread.yield();
					noDelays = 0;
				}
			}
			beforeTime = System.nanoTime();

			int skips = 0;
			while ((excess > period) && (skips < maxFrameSkips)) {
				excess -= period;
				gameUpdate();
				skips++;
			}

		}
		System.exit(0);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// anti aliasing
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// smoother (slower) transformations
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		// AlphaComposite ac =
		AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);

		if (dbImage != null)
			g.drawImage(dbImage, 0, 0, null);
	}

	private void gameUpdate() {
		this.updates.add(System.nanoTime());
		while (this.updates.get(0).longValue() < System.nanoTime() - 2000000000L) {
			this.updates.remove(0);
		}

		// TODO update the game state

		// Scroll Map;
		if (upKey)
			this.mapOffsetY -= scrollValue;
		if (downKey)
			this.mapOffsetY += scrollValue;
		if (leftKey)
			this.mapOffsetX -= scrollValue;
		if (rightKey)
			this.mapOffsetX += scrollValue;
		if (mapOffsetX < 0)
			mapOffsetX = 0;
		if (mapOffsetY < 0)
			mapOffsetY = 0;
		int maxX = map.getPixelWidth() - WIDTH;
		int maxY = map.getPixelHeight() - HEIGHT;
		if (this.mapOffsetX > maxX)
			mapOffsetX = maxX;
		if (this.mapOffsetY > maxY)
			mapOffsetY = maxY;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upKey = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downKey = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftKey = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightKey = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upKey = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downKey = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftKey = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightKey = false;
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public void mouseDragged(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		this.mouse = e.getPoint();
	}
}