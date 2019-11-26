package BlusterCritter;

import java.awt.Color;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;
import info.gridworld.world.World;

public class BlusterCritterRunner {
	/*BlusterCritter颜色为青色，Critter为蓝色*/
	public static void main(String[] args) {
		ActorWorld world = new ActorWorld();
        world.add(new Location(7, 8), new Critter());
        world.add(new Location(3, 3), new Critter());
        world.add(new Location(5, 3), new Critter());
        world.add(new Location(3, 4), new Critter());
        world.add(new Location(3, 5), new Critter());
        world.add(new Location(4, 5), new Critter());
        world.add(new Location(4, 3), new Critter());
        world.add(new Location(5, 4), new Critter());
        world.add(new Location(5, 5), new Critter());
        world.add(new Location(6, 8), new Critter());

        world.add(new Location(4, 4), new BlusterCritter(4));
        world.add(new Location(5, 8), new BlusterCritter());
        world.add(new Location(2, 4), new BlusterCritter(6));
        world.show();
	}

}
