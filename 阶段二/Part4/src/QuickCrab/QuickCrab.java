package QuickCrab;

import java.awt.Color;
import java.util.ArrayList;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class QuickCrab extends CrabCritter{
	/*
	 * 继承CrabCritter类
	 * 重载getMoveLocations
	 * 修改可移动位置的位置选择
	 * 添加getLocationsTwoSpaces方法获取可以移动的空白位置
	 */
	public QuickCrab() {
		setColor(Color.BLUE);
	}
	public ArrayList<Location> getMoveLocations() {
        ArrayList<Location> locations = new ArrayList<Location>();
        Grid gr = getGrid();

        int[] dirs = { Location.LEFT, Location.RIGHT }; //左右方向移动
        for (Location loc : getSecondLocationsInDirections(dirs)) { //获取两空白的左右位置
            if (gr.get(loc) == null) {
            	locations.add(loc);
            }
        }
        /* 如果左右都不存在2个位置全为空白，则调用父类的getMoveLocations方法，即返回左右相邻的位置*/
        if (locations.size() == 0) {
            return super.getMoveLocations();
        }
        return locations;
    }
    /*获取与当前位置左右隔一格，且中间位置也为空的空位置*/
    public ArrayList<Location> getSecondLocationsInDirections(int[] directions) {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid gr = getGrid();
        Location loc = getLocation();
        for (int d : directions) {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            if (gr.isValid(neighborLoc) && getGrid().get(neighborLoc) == null) { //先判断中间位置为空
                Location neightborLoc2 = neighborLoc.getAdjacentLocation(getDirection() + d); //获取左右间隔一个的位置
                if (gr.isValid(neightborLoc2)) { //如果该位置在Grid内
                	locs.add(neightborLoc2);
                }
            }
        }
        return locs;
    }
}
