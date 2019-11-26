import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;


public class JumperTest{
	@Test
	/*turn测试*/	
    public void testTurn() {
    	ActorWorld world = new ActorWorld();
    	Jumper a = new Jumper(Color.RED);
    	world.add(new Location(2,0), a);
    	a.turn();
    	assertEquals(Location.NORTHEAST, a.getDirection());
    }
	
	@Test
    /*单独jump测试*/
    public void testJump() {
    	ActorWorld world = new ActorWorld();
    	Jumper a = new Jumper(Color.RED);
    	world.add(new Location(4,4), a);
    	a.jump();
    	/*jump后行为2，列为4*/
    	assertEquals(2, a.getLocation().getRow());
    	assertEquals(4, a.getLocation().getCol());
    }
	
	@Test
    /*canJump函数测试*/
    public void testCanJump() {
    	ActorWorld world = new ActorWorld();
    	Jumper a = new Jumper(Color.RED);
    	world.add(new Location(4,4), a);
    	/*下一目标为空或者flower时canJump*/
    	boolean tmp = a.canJump();
    	assertTrue(tmp);
    	world.add(new Location(2,4), new Flower());
    	tmp = a.canJump();
    	assertTrue(tmp);
    	/*下一位置为Rock、Jumper、Bug或者Grid边界时不能jump*/
    	Jumper b = new Jumper();
    	Jumper c = new Jumper();
    	Jumper d = new Jumper();
    	Jumper e = new Jumper();
    	/*rock*/
    	world.add(new Location(5,5), b);
    	world.add(new Location(3,5), new Rock());
    	tmp = b.canJump();
    	assertFalse(tmp);
    	/*jumper*/
    	world.add(new Location(5,6), c);
    	world.add(new Location(3,6), new Jumper());
    	tmp = c.canJump();
    	assertFalse(tmp);
    	/*bug*/
    	world.add(new Location(5,7), d);
    	world.add(new Location(3,7), new Bug());
    	tmp = d.canJump();
    	assertFalse(tmp);
    	/*grid边界*/
    	world.add(new Location(1,5), e);
    	tmp = e.canJump();
    	assertFalse(tmp);
    }
    
	@Test
    public void testAct() {
        ActorWorld world = new ActorWorld();
        /*act测试1：遇到flower和空白:act调用jump*/
        Jumper a = new Jumper(Color.RED);
        world.add(new Location(4,4), a);
        a.act();
        assertEquals(2, a.getLocation().getRow());
        assertEquals(4, a.getLocation().getCol());
        
        Flower f = new Flower();
        world.add(new Location(0,4), f);
        a.act();
        assertEquals(0, a.getLocation().getRow());
        assertEquals(4, a.getLocation().getCol());
        
        /*act测试2：遇到rock、jumper、bug、边界调用turn*/
        Jumper b = new Jumper();
    	Jumper c = new Jumper();
    	Jumper d = new Jumper();
    	Jumper e = new Jumper();
    	/*rock*/
    	world.add(new Location(5,5), b);
    	world.add(new Location(3,5), new Rock());
    	b.act();
    	assertEquals(Location.NORTHEAST, b.getDirection());
    	/*jumper*/
    	world.add(new Location(5,6), c);
    	world.add(new Location(3,6), new Rock());
    	c.act();
    	assertEquals(Location.NORTHEAST, c.getDirection());
    	/*bug*/
    	world.add(new Location(5,7), d);
    	world.add(new Location(3,7), new Bug());
    	d.act();
    	assertEquals(Location.NORTHEAST, d.getDirection());
    	/*grid边界*/
    	world.add(new Location(1,5), e);
    	e.act();
    	assertEquals(Location.NORTHEAST, e.getDirection());
    	
    	/*act测试3：遇到jumper、bug时的优先级测试...*/
    	
    }
}