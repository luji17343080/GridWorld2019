## Set 10
The source code for the AbstractGrid class is in Appendix D.  

1.Where is the isValid method specified? Which classes provide an implementation of this method?  

答：`isValid`方法在`Grid`接口中被定义  
```java

    // @file: info/grid/Grid.java
    // @line: 43-50
    /**
     * Checks whether a location is valid in this grid. <br />
     * Precondition: <code>loc</code> is not <code>null</code>
     * @param loc the location to check
     * @return <code>true</code> if <code>loc</code> is valid in this grid,
     * <code>false</code> otherwise
     */
    boolean isValid(Location loc);
```  
在`BoundedGrid`类和`UnbounedGrid`类中被实现  
   ```java
    // @file: info/grid/BoundedGrid.java
    // @line: 60-64
    public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    // @file: info/grid/UnbounedGrid.java
    // @line: 53-55
    public boolean isValid(Location loc)
    {
        return true;
    }
   ```
2.Which AbstractGrid methods call the isValid method? Why don’t the other methods need to call it?  
  
答：`getValidAdjacentLocations`方法：用`isValid`方法判断一个位置的相邻位置是否在边界内。实际上另外几个方法的实现也有这一层逻辑，但包括`getEmptyAdjacentLocations`方法、`getOccupiedAdjacentLocations`方法都调用了`getValidAdjacentLocations`方法，所以就不需要再调用`isValid`方法，而`getNeighbors`方法又调用了`getOccupiedAdjacentLocations`方法，所以也不需要调用`isValid`方法。  
   ```java
    // @file: info/grid/AbstractGrid.java
    // @line: 36-49
    public ArrayList<Location> getValidAdjacentLocations(Location loc)
    {
        ArrayList<Location> locs = new ArrayList<Location>();

        int d = Location.NORTH;
        for (int i = 0; i < Location.FULL_CIRCLE / Location.HALF_RIGHT; i++)
        {
            Location neighborLoc = loc.getAdjacentLocation(d);
            if (isValid(neighborLoc))
                locs.add(neighborLoc);
            d = d + Location.HALF_RIGHT;
        }
        return locs;
    }
   ```
3.Which methods of the Grid interface are called in the getNeighbors method? Which classes provide implementations of these methods?  

答：`getNeighbors`方法中调用了`Grid`接口中的`get`方法。`get`方法在`BoundedGrid`类和`UnbounedGrid`类中被分别实现   
   ```java
    // @file: info/grid/Grid.java
    // @line: 72-79 
    // Grid接口中的get方法
    E get(Location loc);
    // @file: info/grid/AbstractGrid.java
    // @line: 28-34
    // AbstractGrid class中的 getNeighbors 方法
    public ArrayList<E> getNeighbors(Location loc)
    {
        ArrayList<E> neighbors = new ArrayList<E>();
        for (Location neighborLoc : getOccupiedAdjacentLocations(loc))
            neighbors.add(get(neighborLoc));
        return neighbors;
    }

    // @file: info/grid/BoundedGrid.java
    // @line: 85-91
    // `BoundedGrid`类中的get
    public E get(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        return (E) occupantArray[loc.getRow()][loc.getCol()]; // unavoidable warning
    }
    // @file: info/grid/UnboundedGrid.java
    // @line: 66-71
    // `UnboundedGrid`类中的get
    public E get(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc == null");
        return occupantMap.get(loc);
    }
   ```  
4.Why must the get method, which returns an object of type E, be used in the getEmptyAdjacentLocations method when this method returns locations, not objects of type E?  

答：`getEmptyAdjacentLocations`方法只是需要获得空的邻居位置，其中调用`get`方法是为了判断邻居位置是否存在`object`
   ```java
    // @file: info/grid/AbstractGrid.java
    // @line: 51-60
    // getEmptyAdjacentLocations方法
    public ArrayList<Location> getEmptyAdjacentLocations(Location loc)
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        for (Location neighborLoc : getValidAdjacentLocations(loc))
        {
            if (get(neighborLoc) == null)
                locs.add(neighborLoc);
        }
        return locs;
    }
   ```  
5.What would be the effect of replacing the constant Location.HALF_RIGHT with Location.RIGHT in the two places where it occurs in the getValidAdjacentLocations method?  

答：如果用`Location.RIGHT`（90）替换了`Location.HALF_RIGHT`（45），那么在找相邻位置时就会忽略掉`东北`、`东南`、`西南`、`西北`这四个位置，即会在原来的基础上少四个位置，而在整个`Grid`中，我们规定的`actors`是会沿着`45°`方向移动的，所以会造成`actor`的运动改变。   
   ```java
    // @file: info/grid/AbstractGrid.java
    // @line: 36-49
   // getValidAdjacentLocations方法
    public ArrayList<Location> getValidAdjacentLocations(Location loc)
    {
        ArrayList<Location> locs = new ArrayList<Location>();

        int d = Location.NORTH;
        for (int i = 0; i < Location.FULL_CIRCLE / Location.HALF_RIGHT; i++)
        {
            Location neighborLoc = loc.getAdjacentLocation(d);
            if (isValid(neighborLoc))
                locs.add(neighborLoc);
            d = d + Location.HALF_RIGHT;
        }
        return locs;
    }
   ```  
  
## Set 11
The source code for the BoundedGrid class is in Appendix D.  

1.What ensures that a grid has at least one valid location?  

答：`BoundedGrid`类的构造函数：`public BoundedGrid(int rows, int cols)`，其中当rows或者cols小于等于0时会报错。  
   ```java
    // @file: info/grid/BoundedGrid.java
    // @line: 39-46
    public BoundedGrid(int rows, int cols)
    {
        if (rows <= 0)
            throw new IllegalArgumentException("rows <= 0");
        if (cols <= 0)
            throw new IllegalArgumentException("cols <= 0");
        occupantArray = new Object[rows][cols];
    }
   ```
2.How is the number of columns in the grid determined by the getNumCols method? What assumption about the grid makes this possible?  

答：因为使用二维数组的形式存储`Grid`中的`节点`，所以在`getNumCols`，`Grid`的列数`columns`是通过数组的`第0行`的`列数`获得的  
   ```java
    // @file: info/grid/BoundedGrid.java
    // @line: 31
    private Object[][] occupantArray; // the array storing the grid elements
    
    // @file: info/grid/BoundedGrid.java
    // @line: 85-91
    public int getNumCols()
    {
        // Note: according to the constructor precondition, numRows() > 0, so
        // theGrid[0] is non-null.
        return occupantArray[0].length;
    }
   ```

3.What are the requirements for a Location to be valid in a BoundedGrid?  

答：需要该位置的`行数`和`列数`在规定的矩阵范围内（`大于等于0`且小于等于`最大行数`（最大列数））  
   ```java
    // @file: info/grid/BoundedGrid.java
    // @line: 60-64
    // BoundedGrid类中的isValid函数
    public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }
   ```

In the next four questions, let r = number of rows, c = number of columns, and n = number of occupied locations.  

1.What type is returned by the getOccupiedLocations method? What is the time complexity (Big-Oh) for this method?  

答：`getOccupiedLocations`方法会返回一个`Location`的`ArrayList`，它的`size`为n。时间复杂度为`O(r*c)` (`for循环`遍历`Grid`上所有位置)  
   ```java
    // @file: info/grid/BoundedGrid.java
    // @line: 66-80
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> theLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for (int r = 0; r < getNumRows(); r++)
        {
            for (int c = 0; c < getNumCols(); c++)
            {
                // If there's an object at this location, put it in the array.
                Location loc = new Location(r, c);
                if (get(loc) != null)
                    theLocations.add(loc);
            }
        }

        return theLocations;
    }
   ```

2.What type is returned by the get method? What parameter is needed? What is the time complexity (Big-Oh) for this method?  

答：由`get`方法的代码可知，其返回类型为一个`E`类型的`object`；需要的参数为一个`Location`；时间复杂度为`O(1)` (直接访问数组的一个对象)  
   ```java
    // @file: info/grid/BoundedGrid.java
    // @line: 85-91
    public E get(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        return (E) occupantArray[loc.getRow()][loc.getCol()]; // unavoidable warning
    }
   ```
3.What conditions may cause an exception to be thrown by the put method? What is the time complexity (Big-Oh) for this method?  

答：当指定要添加一个`E`类型`object`的`Location`不在`Grid`内以及要添加的`object`为空时会`抛出异常`.时间复杂度为`O(1)` (直接访问数组的一个元素)  
   ```java
    // @file: info/grid/BoundedGrid.java
    // @line: 93-105
    public E put(Location loc, E obj)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (obj == null)
            throw new NullPointerException("obj == null");

        // Add the object to the grid.
        E oldOccupant = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = obj;
        return oldOccupant;
    }
   ```
4.What type is returned by the remove method? What happens when an attempt is made to remove an item from an empty location? What is the time complexity (Big-Oh) for this method?  

答：`remove`方法返回一个`E`类型的`object`；当调用`remove`方法从一个空的位置删除一个`item`时，会抛出异常`Location + "loc" + is not valid`(如果题目中的`空的位置`是指该位置没有任何`object`，那么就不会报错)；时间复杂度为`O(1)`(直接访问数组中的某个元素)  
   ```java
    // @file: info/grid/BoundedGrid.java
    // @line: 107-117
    // remove函数
    public E remove(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        
        // Remove the object from the grid.
        E r = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = null;
        return r;
    }
   ```
5.Based on the answers to questions 4, 5, 6, and 7, would you consider this an efficient implementation? Justify your answer.  

答：根据前面的时间复杂度分析，我认为`BoundedGrid`类的实现是有效的。因为除了`getOccupiedLocations`方法的实现的复杂读达到了O(n^2)，其余都是线性的。  

## Set 12
The source code for the UnboundedGrid class is in Appendix D.  

1.Which method must the Location class implement so that an instance of HashMap can be used for the map? What would be required of the Location class if a TreeMap were used instead? Does Location satisfy these requirements?  

答：在要`实例化`一个`HashMap`，在`Location`类中必须实现`equals`方法、`hashCode`方法和`compareTo`方法；如果要用`TreeMap`替代`HashMap`，则需要在`Location`类中实现一个`Location`的树结构：即要确定`根位置`及`父子关系`  

2.Why are the checks for null included in the get, put, and remove methods? Why are no such checks included in the corresponding methods for the BoundedGrid?  

答：因为在`UnboundedGrid`类中，任何一个`Location`都应该是合法的，只有当程序出错（`Grid`创建错误）时，即某个`Location`未创建成功时才会报错，所以`get`、`put`、`remove`方法中才会先判断`loc`是否创建成功；而在`BoundedGrid`类中只需在`isValid`方法中判断一个位置是否在`Grid`外即可以判断该位置是否创建成功，所以在`get`、`put`和`remove`方法中就只需检验一个`Location`是否合法就行了。  
   ```java
    // @file: info/grid/UnboundedGrid.java
    // @line: 53-56
   // `UnboundedGrid`类中的isValid方法和get方法
    public boolean isValid(Location loc)
    {
        return true;
    }
    // @file: info/grid/UnboundedGrid.java
    // @line: 66-71
    public E get(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc == null");
        return occupantMap.get(loc);
    }

    // @file: info/grid/BoundedGrid.java
    // @line: 60-64
    // `BoundedGrid`类中的isValid方法和get方法
    public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    // @file: info/grid/UnboundedGrid.java
    // @line: 66-71
    public E get(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        return (E) occupantArray[loc.getRow()][loc.getCol()]; // unavoidable warning
    }
   ```  
3.What is the average time complexity (Big-Oh) for the three methods: get, put, and remove? What would it be if a TreeMap were used instead of a HashMap?  

答：`get`, `put`, 以及`remove`方法的平均时间复杂度为`O(1)`（直接访问`HashMap`中的某个元素）；如果用`TreeMap`的话，时间复杂度为`O(logn)`（类似`二叉树`的`查找`、`添加`和`删除`）  

4.How would the behavior of this class differ, aside from time complexity, if a TreeMap were used instead of a HashMap?  

答：如果是`HashMap`，要`查找`、`添加`、`删除``Grid`中的某个`Location`或者其上的`object`，直接通过`索引`访问就行了，这样的访问方式是`线性`的;而如果用`TreeMap`，则需要从`根节点`自顶向下的访问，时间复杂度会更高，访问的效率会大大降低。  

5.Could a map implementation be used for a bounded grid? What advantage, if any, would the two-dimensional array implementation that is used by the BoundedGrid class have over a map implementation?  

答：`Map`可以用于实现有边界`Grid`（Code联系里就会实现）。`二维数组`实现可以通过设置`数组`的大小来分配一个`固定的内存`，相对于`Map`，当`Grid`中的大部分位置都`被占用`时，`二维数组`实现`有界Grid`会节省一些内存。  