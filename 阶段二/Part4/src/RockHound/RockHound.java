package RockHound;

import java.util.ArrayList;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;

public class RockHound extends Critter{
	/*
	 * 继承Critter类
	 * 重载processActors函数
	 * remove Actor列表中的所有Rock对象
	 */
	public void processActors(ArrayList<Actor> actors) {
		for (Actor a : actors) {
			if (a instanceof Rock)
				a.removeSelfFromGrid();
        }
	} 
}
