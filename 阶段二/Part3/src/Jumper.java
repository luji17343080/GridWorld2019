import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Flower;
import java.awt.Color;

public class Jumper extends Actor {
	/*默认构造函数，设置Jumper的默认颜色为red*/
	public Jumper(){
		setColor(Color.RED);
	}
    /*构造函数，设置Jumper的颜色*/
	public Jumper(Color JumperColor) {        
		setColor(JumperColor);    
	}
            
	public void act() {    
		if (canJump())      
			jump();
		else      
			turn();        
	}

	/*定义turn函数为顺时针旋转45度*/        
	public void turn() {           
		setDirection(getDirection() + Location.HALF_RIGHT);         
	} 
        
        
	/*jump函数，与move函数类似，只是每次移动两格*/        
	public void jump()        
	{        	
		Grid<Actor> gr = getGrid();      
		if(gr.equals(null))
			return;            
		Location loc = getLocation();        
		Location next = loc.getAdjacentLocation(getDirection());       
		Location next2 = next.getAdjacentLocation(getDirection());        
		if (gr.isValid(next2))        
			moveTo(next2);            
		else        
			removeSelfFromGrid();                   
	}
    /*canJump函数，与canMove类似，只不过对于Jumper，neighbor变为了与其相邻两格的位置*/            
	public boolean canJump() {            
		Grid<Actor> gr = getGrid();            
		if(gr.equals(null))                
			return false;            
		Location loc = getLocation();            
		Location next = loc.getAdjacentLocation(getDirection());            
		if(!gr.isValid(next))          
			return false;           
		Location next2 = next.getAdjacentLocation(getDirection());           
		if(!gr.isValid(next2))          
			return false;           
		Actor neighbor = gr.get(next2);            
		return (neighbor == null)||(neighbor instanceof Flower);   
		// ok to move into empty location or onto flower
        // not ok to move onto any other actor
	}   
}