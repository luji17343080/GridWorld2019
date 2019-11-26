package DancingBug;

import info.gridworld.actor.Bug;

public class DancingBug extends Bug{
	private int turns[]; //旋转次数数组
	private int steps; //移动步数
	private int counts; //记录旋转的次数
	public DancingBug(int turns_[]) {
		turns = new int[turns_.length];
		System.arraycopy(turns_, 0, turns, 0, turns_.length); //数组拷贝
		steps = 0;
	}
	public void act() {
		int i = steps % turns.length; //循环变量
		if (counts < turns[i]) {
			turn();
			counts++;
		}
		else {
			if (canMove()) move();
			else turn();
			counts = 0;
			steps++;
		}
	}
}
