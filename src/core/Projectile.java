package core;
public class Projectile extends Actor {
	/**
	 * Written by Jack Rivadeneira
	 */

	private Actor parent;

	public Projectile(Actor Parent) {
		super(20);
		speed = 0.8;
		parent = Parent;
		team = parent.team;
		x = parent.x;
		y = parent.y;
		length = 2;
	}

	protected void AI() {
		if (!(Display.withinView(x, y)))
			alive = false;
		/*
		 * int i = 0; while (i < Display.act.size()){ if
		 * (contact(Display.act.get(i)) && Display.act.get(i) != parent) {
		 * Display.act.get(i).alive = false; alive = false; break; } i++; }
		 */
	}

}
