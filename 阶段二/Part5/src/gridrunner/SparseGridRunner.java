package gridrunner;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
/**
 * This class runs a world with additional grid choices.
 */
public class SparseGridRunner {
	public static void main(String[] args) {
		// 创建一个world
	    ActorWorld world = new ActorWorld();
	    // 向world菜单中添加链表实现的SparseBoundedGrid
	    world.addGridClass("LinkList.SparseBoundedGrid");
	    // 向world菜单中添加哈希表实现的SparseBoundedGrid2
	    world.addGridClass("HashMap.SparseBoundedGrid2");
	    // 向world菜单中添加新的UnboundedGrid2
	    world.addGridClass("unboundedgrid.UnboundedGrid2");
	    // 指定位置添加一个occupant: Critter
	    world.add(new Location(2, 2), new Critter());
	    world.show();
	}
}


