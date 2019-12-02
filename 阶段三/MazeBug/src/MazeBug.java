import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	
	private static final int FOUR = 4;
    private static final int RIGHT = 90;
    private static final int CIRCLE = 360;
    
    private Location next;
    private Location last;
    private boolean isEnd = false;
    private Stack<ArrayList<Location>> crossLocation = new Stack<ArrayList<Location>>();
    private Integer stepCount = 0;
    //final message has been shown
    private boolean hasShown = false;
    // 方向数组：改变转动方向为4个：上为0，左为1，下为2，右为3
    private int directions[] = new int[FOUR];
    // 当前方向
    private int nowDir;

    /**
     * Constructs a box bug that traces a square of a given side length
     * 
     * @param length
     *            the side length
     */
    public MazeBug() {
        setColor(Color.GREEN);
        last = new Location(0, 0);
        ArrayList<Location> valid = new ArrayList<Location>();
        crossLocation.push(valid);
        for (int i = 0; i < FOUR; i++) {
        	directions[i] = (i == 2 ? 0 : 1);
        }
        nowDir = 0; 
    }
    // 得到真实的Toward方向
    public int getTurnDirection(int locDirection, int moveDirection) {
    	int angle = moveDirection - locDirection;
    	if (angle < 0) angle += CIRCLE;
    	int dir = angle / RIGHT;
    	return dir;
    }
    /**
     * Moves to the next location of the square.
     */
    public void act() {
        boolean willMove = canMove();
        if (isEnd == true) {
        	//to show step count when reach the goal		
			if (hasShown == false) {
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
        } else if (willMove) {
            // 如果下一个位置能移动，则将可移动的位置加入到crossLocation中
            ArrayList<Location> canMoveLocations = new ArrayList<Location> ();
            canMoveLocations.add(getLocation());
            crossLocation.push(canMoveLocations);
            // 同时将last设置为当前位置
            last = getLocation();
            
            if (getValid(getLocation()).size() > 1) {
                // 两个位置的方向 
                int locDirection = getLocation().getDirectionToward(next);
                // bug的移动方向
                int moveDirection = this.getDirection();
                int dir = getTurnDirection(locDirection, moveDirection);
                directions[dir]++;
                nowDir = dir;
            }            
            move();
            //increase step count when move
            stepCount++;
            
        } else {
        	// 回溯
            next = last;
            
            if (getValid(getLocation()).size() > 1) {
            	directions[nowDir]--;
            }
            
            move();
            // 回溯过程步数仍然增加
            stepCount++;
            // 弹出经过位置的栈顶
            crossLocation.pop();
            // 不断寻找上一位置
            if (crossLocation.peek().size() != 0) {
                last = crossLocation.peek().get(0);
            }
            
        }
    }

    /**
     * Find all positions that can be move to.
     * 
     * @param loc
     *            the location to detect.
     * @return List of positions.
     */
    public ArrayList<Location> getValid(Location loc) {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return null;
        }
        ArrayList<Location> valid = new ArrayList<Location>();
        // 遍历四个方向
        for (int i = 0; i < FOUR; i++) {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + i * Location.RIGHT);
            if (gr.isValid(neighborLoc)) {
                Actor a = gr.get(neighborLoc);
                // 遇到终点红色石头停止
                if (a instanceof Rock && a.getColor().equals(Color.RED)) {
                    isEnd = true;
                    valid.add(neighborLoc);
                    break;
                } else if (a == null){
                    valid.add(neighborLoc);
                }
            }
        }
        return valid;
    }

    /* 判断Bug是否可以移动到下一个位置 */
    @Override
    public boolean canMove() {
    	// 获得当前位置，并得到下一个可移动的位置
    	Location location = getLocation();
        ArrayList<Location> nextLocations = getValid(location);
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return false;
        } else if (nextLocations.size() == 0) {
            return false;
        } else {
            int index = 0;
            int max = 0;
            for (Location loc : nextLocations) {
                // 遇到石头寻找下一位置
                if (gr.get(loc) instanceof Rock) {
                    isEnd = true;
                    next = getLocation();
                    return true;
                }
                // 位置方向
                int locDirection = getLocation().getDirectionToward(loc);
                // bug移动方向
                int moveDirection = this.getDirection();
                int dir = getTurnDirection(locDirection, moveDirection); 
                
                if (directions[dir] > directions[max]) {
                    max = index;
                }
                index++;
            }
            next = nextLocations.get(max);
            return true;
        }
    }
    /**
     * Moves the bug forward, putting a flower into the location it previously
     * occupied.
     */
    public void move() {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return;
        }
        Location loc = getLocation();
        if (gr.isValid(next)) {
            setDirection(getLocation().getDirectionToward(next));
            // 将当前位置设置为last
            last = getLocation();
            moveTo(next);
        } else {
            removeSelfFromGrid();
        }
        Flower flower = new Flower(getColor());
        flower.putSelfInGrid(gr, loc);
    }  
}
