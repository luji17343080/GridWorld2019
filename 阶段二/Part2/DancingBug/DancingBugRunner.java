package DancingBug;

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

public class DancingBugRunner {
	public static void main(String[] args) {
		ActorWorld world = new ActorWorld();
		int turns[] = new int[5];
		for (int i = 0; i < turns.length; i++) {
			turns[i] = i + 1; //注意右边加1
		}
		DancingBug a = new DancingBug(turns);
		world.add(new Location(5, 5), a);
		world.show();
	}

}
