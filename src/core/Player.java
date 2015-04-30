package core;

import java.util.Random;

public final class Player extends Actor {
	/**
	 * Written by Jack Rivadeneira
	 */
	private double t, ts;
	public static int lives;
	private boolean gunReady;
	private Actor front;
	public int length;

	public Player() {
		super(100);
		team = 1;
		length = 30;
		front = new Ball(100);
		t = -Math.PI / 2;
		Display.act.add(front);
		gunReady = true;
		lives = 999;
	}

	public Player(int Lives) {
		super(100);
		team = 1;
		front = new Ball(100);
		t = -Math.PI / 2;
		Display.act.add(front);
		gunReady = true;
		lives = Lives;
	}

	protected void AI() {
		if (!front.alive)
			kill();
		control();
		keepIn();
	}

	private void control() {

		int cx = 100 * ActorData.PLAYER_FRONT_DISTANCE;
		int cy = 100 * ActorData.PLAYER_FRONT_DISTANCE;

		cx *= Math.cos(t);
		cy *= Math.sin(t);

		cx += x;
		cy += y ;

		front.x = cx;
		front.y = cy + ActorData.PLAYER_RADIUS * 100;
		ts *= .9;
		if (Display.keyW) {

			// xs += 5 * Math.cos(t);
			// ys -= 5 * Math.sin(t);
			// int speed = (int) Math.sqrt(xs * xs + ys * ys);
			// if (speed > ActorData.MAX_SPEED) {
			// xs *= 0.99;
			// ys *= 0.99;
			// }
			showRocketTrail(1);
		}
		if (Display.keyA) {
			ts += -.003;
		}
		if (Display.keyS) {
			// ys += -5;
		}
		if (Display.keyD) {
			ts += .003;
		}
		if (Display.mouseOne) {
			shoot();
		}

		move();
	}

	protected void move() {
		super.move();
		t += ts;
	}

	private void keepIn() {
		if (x < 0 || x > 80000)
			x = x > 80000 ? 0 : 80000;// ((int) (x / 800)) * (798) + 1;
		if (y < 0 || y > 60000)
			y = y > 60000 ? 0 : 60000;// ((int) (y / 600)) * (598) + 1;
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
		if (gen % 1 == 0) {
			Particle p = new Particle(60);
			p.setLocation(x, y);
			Random r = new Random();
			p.moveToward(front.x, front.y);
			p.xs /= -4;
			p.ys /= -4;
			// p.xs -= (r.nextInt(200)) - 100;
			// p.ys -= (r.nextInt(200)) - 100;
			Display.act.add(p);
		}
	}

	private void showRocketTrail(int k) {
		while (k > 0) {
			showRocketTrail();
			k--;
		}
	}

	protected void kill() {
		int i = 0;
		while (i < 50) {
			Particle p = new Particle(60);
			p.setLocation(x, y);
			// p.speed = -1;
			Random r = new Random();
			p.moveToward(front);
			p.xs = r.nextInt(101) - 50;
			p.ys = r.nextInt(101) - 50;
			p.team = 6;
			Display.act.add(p);
			i++;
		}
		alive = false;
		front.alive = false;
		lives--;
		if (lives == 0)
			Display.gameOver = true;
	}

	public DrawData getDrawData() {
		return new DrawData(x, y, team, ActorData.PLAYER_DIAMETER);
	}

}
