package core;

import java.util.Random;

public final class Player extends Actor {
	/**
	 * Written by Jack Rivadeneira
	 */
	private double t, ts;
	private double ta;
	public static int lives;
	private boolean gunReady;
	private Actor front;

	public Player() {
		super(100);
		speed = 0.03;
		team = 1;
		front = new Ball(100);
		front.team = 1;
		front.length = 15;
		t = -Math.PI / 2;
		ys = .01;
		// Display.act.add(front);
		ta = 0.00001;
		gunReady = true;
		lives = 3;
	}

	public Player(int Lives) {
		super(100);
		speed = 0.03;
		team = 1;
		front = new Ball(100);
		front.team = 1;
		front.length = 15;
		t = -Math.PI / 2;
		ys = .01;
		Display.act.add(front);
		ta = 0.00001;
		gunReady = true;
		lives = Lives;
	}

	protected void AI() {
		if (!front.alive)
			kill();
		control();
		keepIn();
	}

	public Actor getFront() {
		return this.front;
	}

	private void control() {
		// Dampening
		// xs *= 0.999;
		// ys *= 0.999;
		// ts *= 0.999;

		if (Display.mouseControl)
			t = Math.atan2(x - Display.mouseX, Display.mouseY - y) + Math.PI
					/ 2;

		if (Display.keyW) {
			xs += speed * Math.cos(t);
			ys -= speed * Math.sin(t);
			showRocketTrail();
		}
		if (Display.keyA)
			ts -= ta;
		if (Display.keyS) {
			ts *= 0.99;
			xs *= .999;
			ys *= .999;
		}
		if (Display.keyD)
			ts += ta;
		if (Display.mouseOne)
			shoot();
		else
			gunReady = true;
		// keyboard control
		front.x = x + 30 * Math.cos(t);
		front.y = y + 30 * Math.sin(t);
		// mouse control

	}

	protected void move() {
		super.move();
		t += ts;
	}

	private void keepIn() {
		if (x < 0 || x > 800)
			x = x > 800 ? 0 : 800;// ((int) (x / 800)) * (798) + 1;
		if (y < 0 || y > 600)
			y = y > 600 ? 0 : 600;// ((int) (y / 600)) * (598) + 1;
	}

	private void shoot() {
		if (!gunReady)
			return;
		Projectile p = new Projectile(this);
		p.moveToward(front);
		Display.act.add(p);
		gunReady = false;
	}

	private void showRocketTrail() {
		if (gen % 8 == 0) {
			Particle p = new Particle(50, 765);
			p.setLocation(x, y);
			p.speed = -1;
			Random r = new Random();

			p.moveToward(front);
			p.xs -= (r.nextDouble() / 4) - 0.125;
			p.ys -= (r.nextDouble() / 4) - 0.125;
			Display.act.add(p);
		}
	}

	protected void kill() {

		int i = 0;
		while (i < 50) {
			Particle p = new Particle(50, 765);
			p.setLocation(x, y);
			p.speed = -1;
			Random r = new Random();
			p.moveToward(front);
			p.xs = r.nextDouble() - 0.5;
			p.ys = r.nextDouble() - 0.5;
			p.length = 10;
			Display.act.add(p);
			i++;
		}
		alive = false;
		front.alive = false;
		lives--;
		if (lives == 0)
			Display.gameOver = true;
	}

}
