package RockHound;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains critters. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class RockHoundRunner {
    public static void main(String[] args) {
        ActorWorld world = new ActorWorld();
        world.add(new Location(7, 8), new Rock());
        world.add(new Location(3, 3), new Rock());
        world.add(new Location(5, 3), new Rock());
        world.add(new Location(3, 4), new Rock());
        world.add(new Location(3, 5), new Rock());
        world.add(new Location(4, 5), new Rock());
        world.add(new Location(4, 3), new Rock());
        world.add(new Location(5, 4), new Rock());
        world.add(new Location(5, 5), new Rock());
        world.add(new Location(6, 8), new Rock());
        world.add(new Location(2, 8), new Flower(Color.BLUE));
        world.add(new Location(6, 5), new Flower(Color.PINK));
        world.add(new Location(1, 5), new Flower(Color.RED));
        world.add(new Location(7, 2), new Flower(Color.YELLOW));
        world.add(new Location(1, 5), new Bug(Color.PINK));
        world.add(new Location(2, 5), new Bug(Color.RED));
        world.add(new Location(2, 4), new Bug(Color.RED));
        world.add(new Location(4, 4), new RockHound());
        world.add(new Location(5, 8), new RockHound());
        world.show();
    }
}