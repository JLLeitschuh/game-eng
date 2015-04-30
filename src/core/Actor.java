package core;
public abstract class Actor {
	/**
	 * Written by Jack Rivadeneira
	 */
	protected int x = 40000; // divide by 100 for pixel position
	protected int y = 30000;
	protected int xs, ys;
	protected int frequency;
	protected boolean alive;
	protected int team;
	protected int gen;
	public Actor(int freq) {
		frequency = freq;
		alive = true;
		gen = 0;
	}
	/**
	 * calls AI move and requestDraw and then increments gen.
	 */
	public void run() {
		AI();
		move();
		requestDraw();
		gen++;
	}

	public void requestDraw(){
		if (!Display.drawQueueHas(this)) {
			Display.addToDrawQueue(this);
		}
	}
	
	public abstract DrawData getDrawData();
//		return new DrawData(x/100, y/100, team, 30);
//	}

	protected void move() {
		if ((!alive))
			return;
		x += xs;
		y -= ys;
	}

	protected void setVelocity(int X, int Y) {
		xs = X;
		ys = Y;
	}

	protected void moveToward(int X, int Y) {
		X -= x;
		Y = y - Y;
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

	protected boolean inRange(Actor a, int length) {
		if (a != this)
			return (distanceTo(a) <= length);
		return false;
	}

	protected void setLocation(int X, int Y) {
		x = X;
		y = Y;
	}
	
	protected void kill(){
		alive = false;
	}
}
