package KingCrab;

import java.awt.Color;
import java.util.ArrayList;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class KingCrab extends CrabCritter{
	/*
	 * 继承CrabCritter类
	 * 重载processActors函数
	 * 如果处理的actor能向更远方向移动，则将其移动到更远的位置，如果actor不能，则移除它们
	 * 更远方向的定义：KingCrab对着另外actor的方向上的下一个位置
	 */
	public KingCrab() { //初始化颜色为黄色
		setColor(Color.YELLOW);
	}
	
	public void processActors(ArrayList<Actor> actors) {
        for (Actor a : actors) { //遍历getActors返回的所有actors
        	Grid gr = getGrid();
        	//getDirectionToward方法在Location类中，返回当前位置朝着指定位置的方向值
        	int angel = getLocation().getDirectionToward(a.getLocation()); //计算当前位置（KingCrab的位置）到另一个Actor的方向
            Location nextLoc = a.getLocation().getAdjacentLocation(angel);
            //如果该位置在Grid内，且为空，则调用moveTo函数移到该位置
            if (getGrid().isValid(nextLoc) && gr.get(nextLoc) == null) {
                a.moveTo(nextLoc);
            }
            //否则将其从Grid中移除
            else {
            	a.removeSelfFromGrid();
            }  
        }
    }
}
