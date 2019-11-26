package CircleBug;

import info.gridworld.actor.Bug;

public class CircleBug extends Bug {
	private int steps; 
    private int sideLength; //每一次改变方向之前能移动的长度

    public CircleBug(int length) {
        steps = 0;
        sideLength = length;
    }

    public void act() {
        if (steps < sideLength && canMove()) {
            move();
            steps++;
        }
        else { //每次只转一次
            turn();
            steps = 0;
        }
    }
}
