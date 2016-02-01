package core;
public class ActorManager implements Runnable {
	/**
	 * Written by Jack Rivadeneira
	 */
	private Actor[] act;
	private Thread t;
	private boolean alive;
	private int gen;

	public ActorManager(int Size) {
		act = new Actor[Size];
		alive = true;
		gen = 0;
		t = new Thread(this);
		init();
	}

	public void go() {
		t.start();
	}

	public void run() {
		init();
		while (alive) {
			runActors();
			addAsteriods();
			if (gen % 500 == 0)
				delay(5);
			gen++;
		}
	}

	private void addAsteriods() {
		if (Asteriod.count == 0) {
//			add(new Asteriod());
//			add(new Asteriod());
//			add(new Asteriod());
//			add(new Asteriod());
		}

	}

	private void init() {
		act[0] = new Player();
		act[1] = ((Player)act[0]).getFront();
//		addAsteriods();
	}

	public void kill() {
		alive = false;
	}

	public Actor get(int i) {
		return act[i];
	}

	public int size() {
		return act.length;
	}

	private void runActors() {
		int i = 0;
		if (!act[0].alive && !Display.gameOver) {
			act[0] = new Player(Player.lives);
		}
		if (Display.gameOver)
			i++;
		while (i < act.length) {
			if (act[i] == null)
				;
			else if (!act[i].alive)
				act[i] = null;
			else if (gen % act[i].frequency == 0)
				act[i].run();
			i++;

		}
	}

	public int gameTime() {
		return gen;
	}

	public void add(Actor a) {
		int i = 1;
		while (i < act.length) {
			if (act[i] == null) {
				act[i] = a;
				return;
			}
			i++;
		}
		System.out.println("Out of room!");
	}

	private void delay(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception ex) {
		}
	}
}
