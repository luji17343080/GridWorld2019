package ChameleonKid;

import java.awt.Color;
import java.util.ArrayList;

import ModifiedChameleonCritter.ModifiedChameleonCritter;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

/* 
 * 重载getActors方法
 * 参考CrabCritter类
 * copy CrabCritter类中的getLocationsInDirections方法获取指定方向的邻居位置
 * 在重载的getActors方法指定方向为：Location.AHEAD, Location.HALF_CIRCLE
 */
public class ChameleonKid extends ModifiedChameleonCritter {
	public ChameleonKid() {}
	public ChameleonKid(Color color) {
		setColor(color);
	}
    public ArrayList<Actor> getActors() {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        int[] dirs = { Location.AHEAD, Location.HALF_CIRCLE };//设置方向为前面和后面
        for (Location loc : getLocationsInDirections(dirs)) {
            Actor a = getGrid().get(loc);
            if (a != null) {
                actors.add(a);
            }
        }
        return actors;
    }
     //CrabCritter类中的方法，因为不能继承，所以直接copy
    public ArrayList<Location> getLocationsInDirections(int[] directions) {
        ArrayList<Location> locs = new ArrayList<Location>();
        @SuppressWarnings("rawtypes")
		Grid gr = getGrid();
        Location loc = getLocation();
        for (int d : directions)
        {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            if (gr.isValid(neighborLoc)) {
                locs.add(neighborLoc);
            }
        }
        return locs;
    }  
}
