package core;

public class DrawData {

	public int x = 400;
	public int y = 300;
	public int team;
	public int gen;
	public int length;

	public DrawData(int X, int Y, int TEAM, int LENGTH, int GEN) {
		this.x = X / 100;
		this.y = Y / 100;
		this.team = TEAM;
		this.gen = GEN;
		this.length = LENGTH;
	}

	/**
	 * 
	 * @param X
	 * @param Y
	 * @param TEAM
	 */
	public DrawData(int X, int Y, int TEAM, int LENGTH) {
		this.x = X / 100;
		this.y = Y / 100;
		this.team = TEAM;
		this.length = LENGTH;
	}

}
