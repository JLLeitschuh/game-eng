package core;

import java.util.Random;

public class Asteriod extends Actor {

	/**
	 * Written by Jack Rivadeneira
	 */

	protected int level;
	public static int count = 0;

	public Asteriod() {
		super(100);
		level = 3;
		team = 2;
		Random r = new Random();
		xs = r.nextInt(101) - 50;
		ys = r.nextInt(101) - 50;
		x = r.nextInt();
		y = r.nextInt();
		count++;
	}

	private Asteriod(int lev) {
		super(200);
		level = lev;
		team = 2;
		count++;
	}

	protected void AI() {
		bounce();
		checkForPlayer();
	}

	private void checkForPlayer() {
		int i = 0;

		while (i < Display.act.size()) {
			Actor a = Display.act.get(i);
			if (a != null) {
				int playerContactRange = (getDiameter() + a.getDrawData().length) *50;
				if (alive)
					if (a != this)
						if (a.team == 1)
							if (Player.lives > 0)
								if (a.distanceTo(this) < playerContactRange) {
									death(a);
									break;
								}

			}
			i++;
		}
	}

	private void bounce() {
		if (x > 80000) {
			x = 0;
		}
		if (x < 0) {
			x = 80000;
		}
		if (y > 60000) {
			y = 0;
		}
		if (y < 0) {
			y = 60000;
		}
	}

	protected void hit() {
		if (level == 1) {
			alive = false;
			Display.Score += 100;
			return;
		}
		if (level == 2) {
			Display.Score += 30;
		}
		Display.Score += 20;
		Asteriod a = new Asteriod(level - 1);
		a.x = x;
		a.y = y;
		a.xs = ys * 2;
		a.ys = -xs * 2;
		Asteriod b = new Asteriod(level - 1);
		b.x = x;
		b.y = y;
		b.xs = -ys * 2;
		b.ys = xs * 2;
		Display.act.add(a);
		Display.act.add(b);
		// if (a.distanceTo(b) < (a.length + b.length) / 2) {
		// a.move();
		// b.move();
		// }
		alive = false;
	}

	protected void death(Actor a) {
		hit();
		a.kill();
		count--;
	}

	protected int getDiameter() {
		return ActorData.ASTEROID_DIAMETER_MULTIPLIER * level;
	}

	public DrawData getDrawData() {
		return new DrawData(x, y, team, getDiameter());
	}

}
