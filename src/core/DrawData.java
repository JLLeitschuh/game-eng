package core;

public class DrawData {
	public double x;
	public double y;
	public int lx, ly;
	public double mass;
	public int length;
	public int team;
	public int lifetime;

	public DrawData(double X, double Y, int LX, int LY, double MASS,
			int LENGTH, int TEAM, int LIFETIME) {
		x = X;
		y = Y;
		lx = LX;
		ly = LY;
		mass = MASS;
		length = LENGTH;
		team = TEAM;
		lifetime = LIFETIME;
	}

	public DrawData(double X, double Y, int LX, int LY, double MASS,
			int LENGTH, int TEAM) {
		x = X;
		y = Y;
		lx = LX;
		ly = LY;
		mass = MASS;
		length = LENGTH;
		team = TEAM;
	}

}
