package ZBug;

import info.gridworld.actor.Bug;

public class ZBug extends Bug{
	private int sideLength;
	private int steps;
	private int counts; //纪录换方向的次数，Z字换两次方向之后就停止了
	public ZBug(int length) {
		sideLength = length;
		counts = 0;
		steps = 0;
	}
	public void act() {
		if(counts < 3 && steps < sideLength && canMove()) {
			move();
			steps++;
		}
		else if (counts  == 0 && canMove()){ //第一次旋转3次
			turn();
			turn();
			turn();
			steps = 0;
			counts++;
		}
		else if (counts == 1 && canMove()) { //第二次旋转5次
			for (int i = 0; i < 5; i++) {
				turn();
			}
			steps = 0;
			counts++;
		}
	}
}
