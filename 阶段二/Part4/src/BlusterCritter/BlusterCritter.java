package BlusterCritter;

import java.awt.Color;
import java.util.ArrayList;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class BlusterCritter extends Critter{
	/*
	 * 继承Critter类
	 * 参照Jumper类获取相邻的两个位置
	 * 参照Flower类改变自身颜色
	 * 重载getActors函数获取24邻域的所有actors
	 * 重载processActors
	 */
	private int courage;//BlusterCritter的勇气值：24邻域的BlusterCritter的数量
	private static final double DARKENING_FACTOR = 0.05; //增加或减少的颜色值
	private int count_;//24邻域内的Critter数目
	
	public BlusterCritter() { //默认构造函数,设置勇气值为12
		setColor(new Color(120,120,120)); //为防止颜色加深变浅过程中RGB值超过255，所以初始化为一个中间值
		courage = 12;
	}
	public BlusterCritter(int c) { //构造函数设置指定勇气值，颜色为红
		setColor(new Color(120,120,120));
		courage = c;
	}
	public int getCourage() { //返回勇气值
		return this.courage;
	}
	/*获取24邻域的actors*/
	public ArrayList<Actor> getActors(){
		ArrayList<Actor> actors = new ArrayList<Actor>();
		Grid<Actor> gr = getGrid();
        if (gr == null) {
            return actors;
        }
        
        int row = getLocation().getRow(); //获取当前位置的行数 
        int col = getLocation().getCol(); //获取当前位置的列数
        /*遍历24邻域的所有位置*/
        for (int i = row - 2;  i <= row + 2; i++) {
            for (int j = col - 2; j <= col + 2; j++) {
                if (!(i == row && j == col)) { //排除当前位置
                    Location l = new Location(i, j);
                    if (gr.isValid(l)) { //位置在界内 
                        Actor a = gr.get(l); //获取位置的actor
                        if (a != null) { //存在actor就将之添加到数组列表中
                            actors.add(a);
                        }
                    }
                }
            }
        }
        return actors;
	}
	
	/*根据courage和周围Critters的数量确定改变自身颜色的方式*/
	public void processActors(ArrayList<Actor> actors) {
		int count = 0; //记录24邻域的Critter数目
		Color c = getColor();
		for (Actor a : actors) {  //遍历actors列表中的所有对象
			if (a instanceof Critter) {
				count++;
			}
		}
		if (count < courage) { //当小于勇气值时，BlusterCritter颜色变亮
			int red = (int) (c.getRed() * (1 + DARKENING_FACTOR));
            int green = (int) (c.getGreen() * (1 + DARKENING_FACTOR));
            int blue = (int) (c.getBlue() * (1 + DARKENING_FACTOR));
            if (red <= 255 && green <= 255 && blue <= 255)	
            	setColor(new Color(red, green, blue));
            return;
		}
		
		else { //当大于或等于勇气值时，BlusterCritter颜色变暗
            int red = (int) (c.getRed() * (1 - DARKENING_FACTOR));
            int green = (int) (c.getGreen() * (1 - DARKENING_FACTOR));
            int blue = (int) (c.getBlue() * (1 - DARKENING_FACTOR));
            if (red >= 0 && green >= 0 && blue >= 0)
            	setColor(new Color(red, green, blue));
            return;
		}
	} 
	
	/*辅助函数：获取24邻域的critter数目*/
	public int getCount() {
		count_ = 0;
		ArrayList<Actor> actors_ = this.getActors();
		for (Actor a : actors_) {
			if (a instanceof Critter) {
				count_++;
			}
		}
		return count_;
	}
}
