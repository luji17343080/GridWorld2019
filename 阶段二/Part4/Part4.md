## Set 7
The source code for the Critter class is in the critters directory  

1.What methods are implemented in Critter?  
答：  
```java
/**
* A critter acts by getting a list of other actors, processing that list,
* getting locations to move to, selecting one of them, and moving to the
* selected location.
*/
public void act();  

public ArrayList<Actor> getActors();//获得邻居位置的Actor  
  
public void processActors(ArrayList<Actor> actors);//处理列表中的Actors  
  
public ArrayList<Location> getMoveLocations();//获得相邻的空位置  
  
public Location selectMoveLocation(ArrayList<Location> locs);//从空位置中随机选择一个位置移动  
  
public void makeMove(Location loc);//移动到指定位置

```
2.What are the five basic actions common to all critters when they act?  
答：
```java
//act函数
public void act()
{
    if (getGrid() == null)
        return;
    ArrayList<Actor> actors = getActors();
    processActors(actors);
    ArrayList<Location> moveLocs = getMoveLocations();
    Location loc = selectMoveLocation(moveLocs);
    makeMove(loc);
}
```  
五个基本行为为：  
- `getActors()`
- `processActors(ArrayList<Actor> actors)`
- `getMoveLocations()`
- `selectMoveLocation(ArrayList<Location> locs)`
- `makeMove(Location loc)`

3.Should subclasses of Critter override the getActors method? Explain.  

答：需要重载，因为`Critter`中的`getActors`方法只是简单的调用`getLocation`方法和`getNeighbors`方法获取相邻一个位置的`Actor`而不附加任何条件，且`getLocation`方法只能获取到当前调用者`Actor`的位置信息，所以当有起他条件时，需要重载`getActors`方法  
```java
//Critter中的getActors方法
public ArrayList<Actor> getActors()
{
    return getGrid().getNeighbors(getLocation());
}
```
4.Describe the way that a critter could process actors.  

答：
- 吃掉除`critter`和`rock`外的其他`actors`  
   ```java
    //Critter中的processActors(ArrayList<Actor> actors)方法会执行这种处理
    public void processActors(ArrayList<Actor> actors 
    {
        for (Actor a : actors)
        {
            if (!(a instanceof Rock) && !(a instanceof Critter))
                a.removeSelfFromGrid();
        }
    }
   ```
- 改变其他`actors`的颜色  
- 让其他`actors`移动  

5.What three methods must be invoked to make a critter move? Explain each of these methods.  

答：
- `public ArrayList<Location> getMoveLocations()`方法;
   ```java
    //可以获取与当前位置相邻的空位置，返回一个Location的ArrayList
    public ArrayList<Location> getMoveLocations()
    {
        return getGrid().getEmptyAdjacentLocations(getLocation());
    }
   ```  
- `selectMoveLocation(ArrayList<Location> locs)`方法；  
   ```java
    //从getMoveLocations()方法返回的Location的ArrayList中随机选择一个位置
    public Location selectMoveLocation(ArrayList<Location> locs)
    {
        int n = locs.size();
        if (n == 0)
            return getLocation();
        int r = (int) (Math.random() * n);
        return locs.get(r);
    }
   ```  
- `makeMove(Location loc)`方法；
   ```java
    //移动到selectMoveLocation方法随机选择的位置（在grid边界内）
    public void makeMove(Location loc)
    {
        if (loc == null)
            removeSelfFromGrid();
        else
            moveTo(loc);
    }
   ```
6.Why is there no Critter constructor?  

答：我认为是可以有构造函数但没必要，虽然其他几个继承`Actor`的子类，比如`Bug`、`Flower`都有其构造函数，不过都是对其颜色进行一个初始设置，但其实他们的父类`Actor`就通过默认构造函数完成了对颜色的初始化设置，所以其实要实例化一个对象，直接调用`Actor`中的构造函数就行了。只是单独为每个子类写一个构造函数，在创建实例的时候更容易区分吧，而且也不用在`Grid`中创建对象时进行选择。  
```java
/**
*Actor类中的构造函数
* Constructs a blue actor that is facing north.
*/
public Actor()
{
    color = Color.BLUE;
    direction = Location.NORTH;
    grid = null;
    location = null;
}
```


## Set 8 
The source code for the ChameleonCritter class is in the critters directory  

1.Why does act cause a ChameleonCritter to act differently from a Critter even though ChameleonCritter does not override act?  

答：因为`ChameleonCritter`类重写了`processActors`方法和`makeMove`方法，而`act`方法会调用这两个方法，所以其实就相当于重写了`act`方法  
```java
//ChameleonCritter类中重写的方法
public void processActors(ArrayList<Actor> actors)
{
    int n = actors.size();
    if (n == 0)
        return;
    int r = (int) (Math.random() * n);

    Actor other = actors.get(r);
    setColor(other.getColor());
}
/**
* Turns towards the new location as it moves.
*/
public void makeMove(Location loc)
{
    setDirection(getLocation().getDirectionToward(loc));
    super.makeMove(loc);
}
```

2.Why does the makeMove method of ChameleonCritter call super.makeMove?  

答：因为`ChameleonCritter`类重写`makeMove`方法只是在父类的基础上先改变了自身的方向，而后面的移动行为是不变的，所以可以直接通过`super`调用`父类`的方法（如果不用`super`会因为`方法名一样`而发生冲突），而不需要再重复写一遍。  
```java
//父类Critter中的makeMove方法
public void makeMove(Location loc)
{
    if (loc == null)
        removeSelfFromGrid();
    else
        moveTo(loc);
}

//子类重载的makeMove方法
public void makeMove(Location loc)
{
    setDirection(getLocation().getDirectionToward(loc));
    super.makeMove(loc);
}
```
3.How would you make the ChameleonCritter drop flowers in its old location when it moves?  

答：类似于`Bug`类中的`move`方法：先用一个变量`loc`记录下原来的位置，再通过调用`makeMove`方法移动位置，再通过调用`Actor类`中的`putSelfInGrid`方法在`loc`位置上放置一个`flower`实例就行了（一般情况下通过调用`getColor()`函数创建一个与`Critter`颜色相同的`flower`实例）
```java
//Bug类中的move方法
public void move()
{
    Grid<Actor> gr = getGrid();
    if (gr == null)
        return;
    Location loc = getLocation();
    Location next = loc.getAdjacentLocation(getDirection());
    if (gr.isValid(next))
        moveTo(next);
    else
        removeSelfFromGrid();
    Flower flower = new Flower(getColor());
    flower.putSelfInGrid(gr, loc);
}

//ChameleonCritter类中的drop_flower函数
public void drop_flower(Location loc) { 
    Grid<Actor> gr = getGrid();
    if (gr == null)
        return;
    Location loc_ = getLocation();
    makeMove(loc); //调用已经写好的makeMove方法使其移动
    if(loc_ != loc) {
        Flower flower = new Flower(getColor());
        flower.putSelfInGrid(gr, loc_);
    }
}
```  
4.Why doesn’t ChameleonCritter override the getActors method?  

答：因为`ChameleonCritter`类的功能的实现不需要重载`getActors`方法，即还是只通过调用`getNeighbors`方法获取八邻域的`actors`而不需要考虑其他位置的情况  
```java
//父类Critter中的getActors方法
public ArrayList<Actor> getActors()
{
    return getGrid().getNeighbors(getLocation());
}
```

5.Which class contains the getLocation method?  

答：由`Critter`类的继承关系可知，`getLocation`方法在`Actor`类中具体实现  
```java
/**
* Gets the location of this actor.
* @return the location of this actor, or <code>null</code> if this actor is
* not contained in a grid
*/
public Location getLocation()
{
    return location;
}
```  
6.How can a Critter access its own grid?  

答：通过调用父类`Actor`中的`getGrid`方法  
```java
/**
* Gets the grid in which this actor is located.
* @return the grid of this actor, or <code>null</code> if this actor is
* not contained in a grid
*/
public Grid<Actor> getGrid()
{
    return grid;
}
```  

## Set 9
The source code for the CrabCritter class is reproduced at the end of this part of GridWorld.  

1.Why doesn’t CrabCritter override the processActors method?  

答：因为父类`Critter`中的`processActors`方法的作用正是移除一个`Actor`的`ArrayList`中的不是`Rock`和`Critter`的对象，这就是`CrabCritter`类需要实现的`eat`功能，所以不需要重载`processActors`方法，在实现功能时只需要调用父类的方法即可。  
```java
//父类Critter中的processActors方法
public void processActors(ArrayList<Actor> actors)
{
    for (Actor a : actors)
    {
        if (!(a instanceof Rock) && !(a instanceof Critter))
            a.removeSelfFromGrid();
    }
}
```
2.Describe the process a CrabCritter uses to find and eat other actors. Does it always eat all neighboring actors? Explain.  

答：  
- 首先通过`public ArrayList<Location> getLocationsInDirections(int[] directions)`方法找到指定方向上的（方向值数组）所有相邻位置，返回一个`Location`的`ArrayList`.（其中调用了`ACtor`类中的`getAdjacentLocation`方法）  
```java
public ArrayList<Location> getLocationsInDirections(int[] directions)
{
    ArrayList<Location> locs = new ArrayList<Location>();
    Grid gr = getGrid();
    Location loc = getLocation();

    for (int d : directions)
    {
        Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
        if (gr.isValid(neighborLoc))
            locs.add(neighborLoc);
    }
    return locs;
} 
```  
- 然后通过调用`getActors`方法获得当前位置的`前方`、`右前方`和`左前方`（调用`getLocationsInDirections`方法）的所有`actors`，返回一个`Actor`的`ArrayList`  
```java
public ArrayList<Actor> getActors()
{
    ArrayList<Actor> actors = new ArrayList<Actor>();
    int[] dirs =
        { Location.AHEAD, Location.HALF_LEFT, Location.HALF_RIGHT };
    for (Location loc : getLocationsInDirections(dirs))
    {
        Actor a = getGrid().get(loc);
        if (a != null)
            actors.add(a);
    }

    return actors;
}
```  
- 最后通过调用`Critter`类中的`processActors`方法处理`getActors()`方法获得的`Actors`，移除其中不是`Rock`和`Critter`的对象。  
```java
//父类Critter中的processActors方法
public void processActors(ArrayList<Actor> actors)
{
    for (Actor a : actors)
    {
        if (!(a instanceof Rock) && !(a instanceof Critter))
            a.removeSelfFromGrid();
    }
}
```  
3.Why is the getLocationsInDirections method used in CrabCritter?  

答：因为`CrabCritter`类需要实现的`eat`功能只需要关注当前位置的`前方`、`左前方`和`右前方`，而不需要关注所有的`八邻域`，目前已有的获取相邻位置的方法`getAdjacentLocation`会得到当前位置指定方向的`单邻域`位置，而`getNeighbors`又会获取到`八邻域`的所有`Actors`，两者都不能很好的完成`eat`功能的实现。所以需要`getLocationsInDirections`方法来获取指定的某些方向（用数组存储`方向值`）的`单个`相邻位置，用于在重载的`getActors`方法中实现获取`CrabCritter`面朝方向的`前方`、`左前方`和`右前方`的`actors`  
```java
//CrabCritter类中的getLocationsInDirections方法
public ArrayList<Location> getLocationsInDirections(int[] directions)
{
    ArrayList<Location> locs = new ArrayList<Location>();
    Grid gr = getGrid();
    Location loc = getLocation();
    
    for (int d : directions)
    {
        Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
        if (gr.isValid(neighborLoc))
            locs.add(neighborLoc);
    }
    return locs;
}
```
4.If a CrabCritter has location (3, 4) and faces south, what are the possible locations for actors that are returned by a call to the getActors method?  

答：`CrabCritter`类中的`getActors`方法只会返回`当前actor朝向`的`前方`、`左前方`和`右前方`上的`actors`。所以可能的位置为：`(4，3)`、`(4，4)`和`(4，5)`  

5.What are the similarities and differences between the movements of a CrabCritter and a Critter?  

答：  
相似性:  
- 都是单格移动（都是`moveTo`相邻的位置）  
- 路线上遇到除`flower`以外的所有`actors`都要改变方向    
- 都不能移出`Grid`  

差异性：  
- `CrabCritter`只能左右移动，且移动过程中会吃掉`前方`、`左前方`和`右前方`的一些`actors`  
- `Critter`可以八个方向上移动，移动过程没有其他附加行为  
```java
//CrabCritter类中的makeMove方法
public void makeMove(Location loc)
{
    if (loc.equals(getLocation()))
    {
        double r = Math.random();
        int angle;
        if (r < 0.5)
            angle = Location.LEFT;
        else
            angle = Location.RIGHT;
        setDirection(getDirection() + angle);
    }
    else
        super.makeMove(loc);
}

//Critter类中的makeMove方法
 public void makeMove(Location loc)
{
    if (loc == null)
        removeSelfFromGrid();
    else
        moveTo(loc);
}
```  
6.How does a CrabCritter determine when it turns instead of moving?  

答：当要移动到的位置为`当前位置`时，需要调转方向；当要移动的位置上有其他不同移动到的`actors`或者`Grid`边界时需要调转方向。  
```java
//CrabCritter类中的makeMove方法
public void makeMove(Location loc)
{
    if (loc.equals(getLocation())) //移动的位置为当前位置时
    {
        double r = Math.random();
        int angle;
        if (r < 0.5)
            angle = Location.LEFT;
        else
            angle = Location.RIGHT;
        setDirection(getDirection() + angle);
    }
    else
        super.makeMove(loc);
}
```  
7.Why don’t the CrabCritter objects eat each other?  

答：因为`CrabCritter`类的`eat`功能是通过调用父类`Critter`中的`processActors`方法实现的，而该方法中只能移除除`Rock`和`Critter`对象外的所有`actors`，所以注定了`CrabCritter`对象之间是不能互相吃的。当然，可以重载`processActors`方法实现`互相吃`的功能，不过这样会比较麻烦，因为会涉及到一个优先级的问题：`到底谁吃谁？`根据`颜色`？`位置`？`移动方向`？总之如果在不修改原有代码的情况下，`CrabCritter`是不能互相吃的。  
```java
//父类Critter中的processActors方法
public void processActors(ArrayList<Actor> actors)
{
    for (Actor a : actors)
    {
        if (!(a instanceof Rock) && !(a instanceof Critter))
            a.removeSelfFromGrid();
    }
}
```