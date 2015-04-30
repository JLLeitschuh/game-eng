package core;

public final class Particle extends Actor {
	int lifetime;
	int R = 0xFF;
	int G = 0xFF;
	int B = 0XFF;
	int stage = 0;
	public Particle(int freq) {
		super(freq);
		team = 5;
	}
	
	public void requestDraw(){
		Display.addToDrawQueue(this);
	}

	
	protected void fire(){
		if (B > 0)
			B-=15;
		else if (G > 0)
			G-=15;
		else if(R > 0)
			R-=15;

		int msk = 0x00FF;
		lifetime = lifetime | (R & msk);
		lifetime = lifetime << 8;
		lifetime = lifetime | (G & msk);
		lifetime = lifetime << 8;
		lifetime = lifetime | (B & msk);
	}
	
	protected void AI() {
		lifetime = 0;
		fire();
			if (lifetime == 0)
			alive = false;
		xs *= .95;
		ys *= .95;
	}

	public DrawData getDrawData() {
		return new DrawData(x, y, team, ActorData.PARTICLE_BURN_TRAIL_DIAMETER,
				lifetime);
	}

}
