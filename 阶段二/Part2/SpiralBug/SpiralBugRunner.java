package SpiralBug;

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import java.awt.Color;

public class SpiralBugRunner {
    public static void main(String[] args) {
        ActorWorld world = new ActorWorld();
        SpiralBug a = new SpiralBug(3);
        a.setColor(Color.ORANGE);
        //SpiralBug b = new SpiralBug(1);
        world.add(new Location(5, 5), a);
        //world.add(new Location(4, 6), b);
        world.show();
    }
}