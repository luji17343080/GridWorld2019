package CircleBug;

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import java.awt.Color;

public class CircleBugRunner {
    public static void main(String[] args) {
        ActorWorld world = new ActorWorld(); //实例化一个world
        CircleBug a = new CircleBug(2); //边长为2
        a.setColor(Color.ORANGE); 
        CircleBug b = new CircleBug(1);
        world.add(new Location(6, 2), a);
        world.add(new Location(5, 4), b);
        world.show();
    }
}