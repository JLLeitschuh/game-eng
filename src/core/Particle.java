package core;

public final class Particle extends Actor {
	int lifetime;

	public Particle(int freq, int Lifetime) {
		super(freq);
		lifetime = Lifetime;
		team = 5;
		length = 30;
	}

	protected void AI() {
		if (lifetime == 0)
			alive = false;
		else {
			lifetime -= 3;
			speed *= .992;
			frequency += 1;
		}

	}

	public DrawData getDrawData() {
		return new DrawData(x, y, lx, ly, mass, length, team, lifetime);
	}

}
