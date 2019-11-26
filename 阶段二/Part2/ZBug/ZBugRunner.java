package ZBug;

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import java.awt.Color;

public class ZBugRunner {

	public static void main(String[] args) {
		ActorWorld world = new ActorWorld();
		ZBug a = new ZBug(3);
		ZBug b = new ZBug(5);
		a.setColor(Color.BLUE);
		a.setDirection(90);
		b.setDirection(90);
		world.add(new Location(5, 5), a);
		world.add(new Location(4, 5), b);
		world.show();

	}

}
