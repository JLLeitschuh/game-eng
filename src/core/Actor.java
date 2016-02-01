package core;
public abstract class Actor {
	/**
	 * Written by Jack Rivadeneira
	 */
	protected double x = 400;
	protected double y = 300;
	protected int lx, ly;
	protected double xs, ys;
	protected double speed;
	protected double mass;
	protected int frequency;
	private boolean locked;
	protected boolean alive;
	protected int length;
	protected int team;
	protected int gen;
	private int lgen;
	public Actor(int freq) {
		frequency = freq;
		mass = 10;
		speed = 0;
		locked = false;
		alive = true;
		length = 30;
		gen = 0;

//		Display.addToDrawQueue(this);
	}

	public void run() {
		AI();
		move();
		if (((int) x != lx || (int) y != ly) || lgen != Display.gen) {
			lx = (int) x;
			ly = (int) y;
			lgen = Display.gen;
		}
		gen++;
	}

	public void lock() {
		locked = true;
	}

	public void unlock() {
		locked = false;
	}
	
	public DrawData getDrawData(){
		return new DrawData(x, y, lx, ly, mass, length, team);
	}

	protected void move() {
		if ((!alive) || locked)
			return;
		x += xs * speed;
		y -= ys * speed;
	}

	protected void setVelocity(double X, double Y) {
		double V = Math.sqrt((X * X) + (Y * Y));
		xs = X / V;
		ys = Y / V;
	}

	protected void moveToward(double X, double Y) {
		X = X - x;
		Y = -(Y - y);
		setVelocity(X, Y);
	}

	protected void moveToward(Actor a) {
		moveToward(a.x, a.y);
	}

	protected abstract void AI();

	protected double distanceTo(Actor a) {
		return distanceTo(a.x, a.y);
	}

	protected double distanceTo(double X, double Y) {
		return Math.sqrt((X - x) * (X - x) + (Y - y) * (Y - y));
	}

	protected boolean contact(Actor a) {
		if (a != this)
			return (distanceTo(a) <= (length + a.length) / 2);
		return false;
	}

	protected void setLocation(double X, double Y) {
		x = X;
		y = Y;
	}
	
	protected void kill(){
		alive = false;
	}
}
