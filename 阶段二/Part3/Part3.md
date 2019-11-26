## Set 3
**Assume the following statements when answering the following questions.**  
- `Location loc1 = new Location(4, 3);`  
- `Location loc2 = new Location(3, 4);`  
1. How would you access the row value for loc1?  
答：通过调用getRow方法：`int row = loc1.getRow()`;  
    ```java
        /**
        * Gets the row coordinate.
        * @return the row of this location
        */
        public int getRow()
        {
            return row;
        }
    ```
2. What is the value of b after the following statement is executed?  
    - `boolean b = loc1.equals(loc2);`  
      
    答：`equals`方法只有当两个`Location`的`row`和`col`都相等时才返回`True`。所以b的值为`false`。  
    ```java
    public boolean equals(Object other)
    {
        if (!(other instanceof Location))
            return false;

        Location otherLoc = (Location) other;
        return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
    }
    ```
3. What is the value of loc3 after the following statement is executed? 
    - `Location loc3 = loc2.getAdjacentLocation(Location.SOUTH);`  

    答：即`loc2`的`SOUTH`方向的邻居：`loc3`的`row`为4，`col`为4.  
4. What is the value of dir after the following statement is executed? 
    - `int dir = loc1.getDirectionToward(new Location(6, 5));`

    答：即`loc1`朝着(6, 5)的方向所代表的值：135（SOUTHEAST）.  
    ```java
    public int getDirectionToward(Location target)
    {
        int dx = target.getCol() - getCol();
        int dy = target.getRow() - getRow();
        // y axis points opposite to mathematical orientation
        int angle = (int) Math.toDegrees(Math.atan2(-dy, dx));

        // mathematical angle is counterclockwise from x-axis,
        // compass angle is clockwise from y-axis
        int compassAngle = RIGHT - angle;
        // prepare for truncating division by 45 degrees
        compassAngle += HALF_RIGHT / 2;
        // wrap negative angles
        if (compassAngle < 0)
            compassAngle += FULL_CIRCLE;
        // round to nearest multiple of 45
        return (compassAngle / HALF_RIGHT) * HALF_RIGHT;
    }
    ```
5. How does the getAdjacentLocation method know which adjacent location to return?  
答：根据方向参数（`整数`），寻找与指定方向最近的`方格的位置`.因为传入的参数`方向值`可能不为45的倍数（即不为规定的八个方向），所以需要先对方向值进行处理使其变为`45的倍数`（即真正意义上的相邻位置方向），然后，对于不同的方向，对原来的`row`和`col`做`加一减一`处理即可。根据源代码，方向值`"45倍化"`处理的过程如下：  
   - 定义相邻方向值为传入参数的方向值加上22.5，然后通过`mod 360`以及`加 360`的方法使其值在[0, 360]范围内
   - 接着通过先`"/ 45"`再`"* 45"`的方式将相邻方向值变为`45的倍数`（即寻找与指定方向最近的八个方向之一）  
   - 最后根据`相邻方向值`对位置的`row`和`col`进行处理就行了  
    ```java
    public Location getAdjacentLocation(int direction)
        {
            // reduce mod 360 and round to closest multiple of 45
            int adjustedDirection = (direction + HALF_RIGHT / 2) % FULL_CIRCLE;
            if (adjustedDirection < 0)
                adjustedDirection += FULL_CIRCLE;

            adjustedDirection = (adjustedDirection / HALF_RIGHT) * HALF_RIGHT;
            int dc = 0;
            int dr = 0;
            if (adjustedDirection == EAST)
                dc = 1;
            else if (adjustedDirection == SOUTHEAST)
            {
                dc = 1;
                dr = 1;
            }
            else if (adjustedDirection == SOUTH)
                dr = 1;
            else if (adjustedDirection == SOUTHWEST)
            {
                dc = -1;
                dr = 1;
            }
            else if (adjustedDirection == WEST)
                dc = -1;
            else if (adjustedDirection == NORTHWEST)
            {
                dc = -1;
                dr = -1;
            }
            else if (adjustedDirection == NORTH)
                dr = -1;
            else if (adjustedDirection == NORTHEAST)
            {
                dc = 1;
                dr = -1;
            }
            return new Location(getRow() + dr, getCol() + dc);
        }
    ```
   
## Set 4
1. How can you obtain a count of the objects in a grid? How can you obtain a count of the empty locations in a bounded grid?  
答：
    - 调用`ArrayList<Location> getOccupiedLocations()`函数获得`Grid`中所有被占用的位置数组信息，再通过获得数组的`size`得出Grid中的`objects`数目  
        ```java
        public ArrayList<Location> getOccupiedLocations()
        {
            ArrayList<Location> theLocations = new ArrayList<Location>();

            // Look at all grid locations.
            for (int r = 0; r < getNumRows(); r++)
            {
                for (int c = 0; c < getNumCols(); c++)
                {
                    Location loc = new Location(r, c);
                    if (get(loc) != null)
                        theLocations.add(loc);
                }
            }

            return theLocations;
        }
        ```

    - 通过调用`getNumRows()`函数和`getNumCols()`函数获得有界`Grid`的行数和列数，再将行数和列数相乘得到`Grid总网格数`，再用`总网格数`减去前面得到的被占用的位置数就可以得到空的位置数  
    
2. How can you check if location (10,10) is in a grid?  
答：通过调用`boolean isValid(Location loc)`函数判断，如果返回值为`True`则表示在`Grid`内。  
    ```java
    // BoundedGrid类中的isValid函数
    public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }
    ```
  
3. Grid contains method declarations, but no code is supplied in the methods. Why? Where Can you find the implementations of these methods?  
答：`Grid`本身只是一个接口，它只定义一些函数方法。而这些函数方法的实现是在一些具体的类中，比如 `AbstractGrid`类, `BoundedGrid`类和 `UnboundedGrid`类，这些类通过`implements`方法然后根据自己的需求来实现`Grid`接口中函数方法。  
   ```java
    // Grid接口
    public interface Grid<E> {
        int getNumRows();
        int getNumCols();
        boolean isValid(Location loc);
        E put(Location loc, E obj);
        E remove(Location loc);
        E get(Location loc);
        ArrayList<Location> getOccupiedLocations();
        ArrayList<Location> getValidAdjacentLocations(Location loc);
        ArrayList<Location> getEmptyAdjacentLocations(Location loc);
        ArrayList<Location> getOccupiedAdjacentLocations(Location loc);
        ArrayList<E> getNeighbors(Location loc);
    }
   ```
  
4. All methods that return multiple objects return them in an ArrayList. Do you think it would be a better design to return the objects in an array? Explain your answer.  
答：用一个固定大小的`array`不会更好。因为如果要用`array`，为了防止越界，它的空间至少要为整个`Grid`，而由前面我们知道返回多个对象的函数基本上不需要这样大的空间。因此用`array`显然会浪费一定的空间，而`ArrayList`就可以避免这样的浪费，因为它可以根据需求自动调整大小。  
  
## Set 5  
1. Name three properties of every actor.  
答：`Color`, `Direction`, `Location`.  
   ```java
    // Actor类的成员变量
    private Grid<Actor> grid;
    private Location location;
    private int direction;
    private Color color;
   ```

2. When an actor is constructed, what is its direction and color?  
答：默认的`direction`是`NORTH`，默认的`color`是`BLUE`  
   ```java
    // Actor类的默认构造函数
    public Actor()
    {
        color = Color.BLUE;
        direction = Location.NORTH;
        grid = null;
        location = null;
    }
   ```

3. Why do you think that the Actor class was created as a class instead of an interface?  
答：因为`Actor class`中对函数方法进行了具体的实现  
   ```java
    // 如下
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    public int getDirection()
    {
        return direction;
    }

    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int newDirection)
    {
        direction = newDirection % Location.FULL_CIRCLE;
        if (direction < 0)
            direction += Location.FULL_CIRCLE;
    }
   ```
  
4. Can an actor put itself into a grid twice without first removing itself? Can an actor remove itself from a grid twice? Can an actor be placed into a grid, remove itself, and then put itself back? Try it out. What happens?  
答：
   - 一个`actor`不能将自己放入`Grid`两次，由`putSelfInGrid`方法中的如下语句体现：
        ```java
        Actor actor = gr.get(loc);
        if (actor != null)
            actor.removeSelfFromGrid();
        gr.put(loc, this);
        ```  
   - 一个`actor`不能从一个`Grid`中移除两次，`removeSelfFromGrid()`函数如下：当第一次remove之后，`Location`会变为空，当第二次remove时，`grid.remove(location)`中的`location`为空，会报错
        ```java
        public void removeSelfFromGrid()
        {
        if (grid == null)
            throw new IllegalStateException(
                    "This actor is not contained in a grid.");
        if (grid.get(location) != this)
            throw new IllegalStateException(
                    "The grid contains a different actor at location "
                            + location + ".");

        grid.remove(location);
        grid = null;
        location = null;
        }
        ```  
   - 一个`actor`能先放入`Grid`中，然后被移除，最后在放回来  

5. How can an actor turn 90 degrees to the right?  
答：通过调用`public void setDirection(int newDirection)`方法，其中`newDirection`为`getDirection() + Location.RIGHT`，即当前方向值加90.  
   ```java
   public void setDirection(int newDirection)
    {
        direction = newDirection % Location.FULL_CIRCLE;
        if (direction < 0)
            direction += Location.FULL_CIRCLE;
    }
   ```
  
## Set 6
1. Which statement(s) in the canMove method ensures that a bug does not try to move out of its grid?  
答：
    ```java
    if (gr.isValid(next)) 
        moveTo(next);
    else removeSelfFromGrid
    ```
2. Which statement(s) in the canMove method determines that a bug will not walk into a rock?  
答：
    ```java
    return (neighbor == null) || (neighbor instanceof Flower)
    ```
3. Which methods of the Grid interface are invoked by the canMove method and why?  
答：
   - `boolean isValid(Location loc)`方法，用于判断下一个`Location`是否在`Grid`界内，避免`actor`出界，具体在`canMove()`方法中的语句如下：  
        ```java
        if (gr.isValid(next)) 
            moveTo(next);
        else removeSelfFromGrid();
        ```
   - `E get(Location loc)`方法，用于获取当前`actor`邻居位置的`actor`，以此避免`Bug`经过`Rock`等障碍，具体在`canMove()`方法中的实现如下：  
        ```java
        Actor neighbor = gr.get(next);
        return (neighbor == null) || (neighbor instanceof Flower)
        ```
4. Which method of the Location class is invoked by the canMove method and why?  
答：  
   - `public Location getAdjacentLocation(int direction)`方法，用于获取指定方向的邻居位置  
       ```java
       //canMove()方法中的语句
       Location loc = getLocation();
       Location next = loc.getAdjacentLocation(getDirection());
       ```
5. Which methods inherited from the Actor class are invoked in the canMove method?  
答：  
   - `getDirection()`方法，用于获取当前位置的方向值
      ```java
      // canMove()方法中的语句
      Location next = loc.getAdjacentLocation(getDirection());
      ```
   - `getGrid()`方法，用于获取当前位置的方向值
      ```java
      // canMove()方法中的语句
      Grid<Actor> gr = getGrid();
      ```
6. What happens in the move method when the location immediately in front of the bug is out of the grid?  
答：会调用`Actor`类中的`removeSelfFromGrid`方法将自身从`Grid`中移除，并在原地留下一朵`Flower`  
   ```java
    if (gr.isValid(next))
        moveTo(next);
    else
        removeSelfFromGrid();
    Flower flower = new Flower(getColor());
    flower.putSelfInGrid(gr, loc);
   ```
7. Is the variable loc needed in the move method, or could it be avoided by calling getLocation() multiple times?  
答：需要变量`loc`，因为当下一个位置可移动时，在`move`方法中会先移动位置（即`getLocation()`方法的返回值会改变），然后再在原来的位置处通过`flower.putSelfInGrid(Grid<Actor>, Location)`方法放置一朵`Flower`，而此处的`Location`是原来的位置，如果只是多次调用`getLocation()`方法来获取位置的话，并不能获取到该位置，所以需要一个`loc`变量存储原来的位置。当然，如果考虑到不管下一个位置是否合法都会产生一个`Flower`的话，可以改变语句执行的顺序，不过这样实现起来会比较麻烦。所以总的来说，`loc`变量还是需要的。  
    ```java
    // 原来的语句执行顺序
    if (gr.isValid(next))
        moveTo(next);
    else
        removeSelfFromGrid();
    Flower flower = new Flower(getColor());
    flower.putSelfInGrid(gr, loc);
    ```
8. Why do you think the flowers that are dropped by a bug have the same color as the bug?  
答：根据`move`方法的如下语句，可以看到`bug`移动过程中创建的`flower`的颜色是通过调用`getColor()`方法获得的，也就是说获得的是`bug`的`color`
   ```java
   Flower flower = new Flower(getColor());
   flower.putSelfInGrid(gr, loc);
   ```
9. When a bug removes itself from the grid, will it place a flower into its previous location?  
答：会，由第7题`move`方法中的语句执行顺序可以看出，当执行了`removeSelfFromGrid()`方法之后，原来的位置会被`flower`代替。  
  
10. Which statement(s) in the move method places the flower into the grid at the bug’s previous location?  
答：  
    ```java
    Flower flower = new Flower(getColor());
    flower.putSelfInGrid(gr, loc);
    ```
11. If a bug needs to turn 180 degrees, how many times should it call the turn method?  
答：`turn`方法会每次`顺时针`旋转`45°`。因此，如果要旋转180°，需要调用4次`trun`（这里的旋转只能是顺时针旋转）
