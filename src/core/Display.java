package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Display extends JFrame implements MouseListener, KeyListener,
		MouseMotionListener {
	/**
	 * Written by Jack Rivadeneira
	 */
	private static final long serialVersionUID = 1L;

	public static int gen;
	public static Graphics2D bg;
	public static Image buff;
	public static ActorManager act;
	private static ArrayList<DrawData> drawQueue = new ArrayList<DrawData>();
	public static int clickX, clickY;
	public static int mouseX, mouseY;

	public static boolean mouseControl = false;

	public static boolean keyW, keyA, keyS, keyD, keyQ, mouseOne;

	public static boolean gameOver;

	public static int Score = 0;

	public Display() {
		setSize(800, 600);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Asteroids Redux");
		setBackground(Color.BLACK);
		addMouseListener(this);
		addKeyListener(this);
		addMouseMotionListener(this);
		setUndecorated(true);
		act = new ActorManager(200);
		gen = 0;
		setVisible(true);
	}

	public void init() {
		buff = createImage(800, 600);
		bg = (Graphics2D) buff.getGraphics();
		bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		bg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		act.go();
	}

	public void paint(Graphics g) {
		if (gen == 0)
			init();
		if (gameOver)
			drawGameOver();
		drawActors();
		update(g);
		repaint();
	}

	public void drawScore() {
		bg.setColor(Color.WHITE);
		bg.setFont(new Font("Lucida Console", Font.PLAIN, 32));
		bg.setColor(Color.WHITE);
		bg.drawString("Score: " + Score, 10, 30);
		bg.setColor(Color.WHITE);
		bg.drawString("Lives: " + Player.lives, 10, 60);
	}

	public synchronized void drawActors() {
		ArrayList<DrawData> queue = new ArrayList<DrawData>();
		queue.addAll(drawQueue);
		gen++;
		while (queue.size() > 0) {
			drawActor(queue.get(0));
			drawQueue.remove(0);
			queue.remove(0);
		}
	}

	public synchronized void drawGameOver() {
		bg.setColor(Color.WHITE);
		bg.setFont(new Font("Lucida Console", Font.PLAIN, 32));
		bg.drawString("Game Over", 300, 300);
	}

	public static void addToDrawQueue(Actor a) {
		drawQueue.add(a.getDrawData());
	}

	public static boolean drawQueueHas(Actor a) {
		return drawQueue.contains(a);
	}

	private void drawActor(DrawData a) {
		if (a != null) {
			if (a.team == 1) {
				bg.setColor(Color.YELLOW);
				bg.fillOval(a.x - a.length / 2, a.y + a.length / 2, a.length,
						a.length);
				return;
			}
			if (a.team == 2) {
				bg.setColor(Color.WHITE);
				bg.drawOval(a.x - a.length / 2, a.y + a.length / 2, a.length,
						a.length);
			}
			if (a.team == 5) {
				int msk = 0x00FF;
				int r, g, b;
				r = (a.gen >> 16) & msk;
				g = (a.gen >> 8) & msk;
				b = (a.gen) & msk;
				bg.setColor(new Color(r, g, b));
				bg.fillOval(a.x - a.length / 2, a.y + a.length / 2, a.length,
						a.length);
			}

		}
	}

	public void delay(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception ex) {
		}

	}

	public void update(Graphics g) {
		delay(1);
		g.drawImage(buff, 0, 0, this);
		if (gen % 200 == 0)
			bg.clearRect(0, 0, 800, 600);
		drawScore();
	}

	public static boolean withinView(double X, double Y) {
		return !(X > 80000 || X < 0 || Y > 60000 || Y < 0);
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			mouseOne = true;
			clickX = e.getX();
			clickY = e.getY();
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			mouseOne = false;
	}

	public void keyPressed(KeyEvent e) {
		String c = "" + e.getKeyChar();
		if (c.equalsIgnoreCase("w"))
			keyW = true;
		if (c.equalsIgnoreCase("a"))
			keyA = true;
		if (c.equalsIgnoreCase("s"))
			keyS = true;
		if (c.equalsIgnoreCase("d"))
			keyD = true;
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
	}

	public void keyReleased(KeyEvent e) {
		String c = "" + e.getKeyChar();
		if (c.equalsIgnoreCase("w"))
			keyW = false;
		if (c.equalsIgnoreCase("a"))
			keyA = false;
		if (c.equalsIgnoreCase("s"))
			keyS = false;
		if (c.equalsIgnoreCase("d"))
			keyD = false;
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		String c = "" + e.getKeyChar();
		if (c.equalsIgnoreCase("m"))
			mouseControl = !mouseControl;

	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
}
