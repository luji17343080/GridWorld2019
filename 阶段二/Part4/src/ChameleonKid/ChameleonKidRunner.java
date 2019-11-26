package ChameleonKid;

import java.awt.Color;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

public class ChameleonKidRunner {
	public static void main(String[] args) {
		ActorWorld world = new ActorWorld();
		world.add(new Location(7, 5), new Bug());
        world.add(new Location(7, 5), new Rock(Color.BLUE));
        world.add(new Location(6, 5), new ChameleonKid());
        world.add(new Location(5, 5), new Rock(Color.GREEN));
        world.add(new Location(7, 8), new Rock(Color.RED));
        world.add(new Location(6, 8), new ChameleonKid());
        world.add(new Location(5, 8), new Rock());
        world.add(new Location(3, 3), new Rock(Color.PINK));
        world.add(new Location(7, 3), new Rock(Color.GRAY));
        world.add(new Location(5, 3), new Rock(Color.ORANGE));
        world.add(new Location(2, 4), new ChameleonKid(Color.WHITE));
        world.show();
	}
}
