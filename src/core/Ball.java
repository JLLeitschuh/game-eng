package core;

public class Ball extends Actor{

	public Ball(int freq) {
		super(freq);
		team = 1;
	}
	protected void AI(){
		
	}
	public DrawData getDrawData() {
		return new DrawData(x,y,team, ActorData.PLAYER_FRONT_DIAMETER);
	}
}
