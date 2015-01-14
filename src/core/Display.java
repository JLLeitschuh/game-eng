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
	private static boolean stopdraw;
	private static ArrayList<DrawData> drawQueue= new ArrayList<DrawData>();
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
	
	public synchronized void drawActors(){
		ArrayList<DrawData> queue = new ArrayList<DrawData>();
		queue.addAll(drawQueue);
		gen++;
		while(queue.size()>0){
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
	
	public static void addToDrawQueue(Actor a){
		drawQueue.add(a.getDrawData());
	}

	private void drawActor(DrawData a) {
		if (stopdraw)
			return;
		if (a != null) {
			int r = 255;
			int g = 255;
			int b = 255;
			boolean rec = false;
			boolean fill = true;
			if (a.team == 2)
				fill = false;// r = b = 255;
			if (a.team == 1)
				b = 255;
			if (a.team == 6) {
				r = (int) a.lifetime;
				g = 0;
				b = 0;
			}
			if (a.team == 5) {
				int life = (int) a.lifetime;
				if (life < 256) {
					r = life;
					g = 0;
					b = 0;
				} else if (life < 511) {
					g = life - 256;
					b = 0;
				} else
					b = life % 256;
				rec = false;
			}
			int w = a.length;
			Color c = (new Color(r, g, b));
			bg.setColor(c);

			if (fill) {
				if (rec)
					bg.fillRect((int) a.x - (w / 2), (int) a.y - (w / 2), w, w);
				else
					bg.fillOval((int) a.x - (w / 2), (int) a.y - (w / 2), w, w);
			} else {
				if (rec)
					bg.drawRect((int) a.x - (w / 2), (int) a.y - (w / 2), w, w);
				else
					bg.drawOval((int) a.x - (w / 2), (int) a.y - (w / 2), w, w);
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
		stopdraw = true;
		g.drawImage(buff, 0, 0, this);
		bg.clearRect(0, 0, 800, 600);
		drawScore();
		stopdraw = false;
		
	}

	public static boolean withinView(double X, double Y) {
		return !(X > 800 || X < 0 || Y > 600 || Y < 0);
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
