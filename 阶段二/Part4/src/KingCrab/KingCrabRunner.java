package KingCrab;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

public class KingCrabRunner {
	/*KingCrab默认为黄色，CrabCritter为红色*/
	public static void main(String[] args) {
		ActorWorld world = new ActorWorld();
        world.add(new Location(7, 5), new Rock());
        world.add(new Location(5, 4), new Rock());
        world.add(new Location(5, 7), new Rock());
        world.add(new Location(7, 3), new Rock());
        world.add(new Location(7, 8), new Flower());
        world.add(new Location(2, 3), new Flower());
        world.add(new Location(2, 4), new Rock());
        world.add(new Location(3, 5), new Bug());
        world.add(new Location(3, 8), new Flower());
        world.add(new Location(4, 6), new Flower());
        world.add(new Location(4, 4), new Flower());
        world.add(new Location(3, 4), new Bug());
        
        world.add(new Location(6, 1), new CrabCritter());
        world.add(new Location(6, 3), new Flower());
        world.add(new Location(4, 5), new KingCrab());
        world.add(new Location(2, 5), new Rock());
        Bug b = new Bug();
        b.setDirection(270);
        world.add(new Location(3, 7), b);
        world.show();
	}
}
