# Set 2 The source code for the BoxBug class can be found in the boxBug directory.  
1. What is the role of the instance variable sideLength?  
答：sideLength表示`Bug`能移动的正方形范围的边长`-1`.即`sideLength`规定了`Bug`能移动的范围。
2. What is the role of the instance variable steps?  
答：steps记录了`Bug`移动的步数，当到达移动范围边界时，steps会重新置0.即`steps`用于判断Bug是否到达移动边界。
3. Why is the turn method called twice when steps becomes equal to sideLength?  
答：因为调用`trun`方法每次只会让`Bug`顺时针旋转`45°`，而要能在指定范围的正方形内移动，`Bug`每次至少要顺时针旋转`90°`，所以当`steps`等于`sideLength`时，`Bug`需要调用两次`trun`方法。  
   ```java
    // turn方法
    /**
     * Turns the bug 45 degrees to the right without changing its location.
     */
    public void turn()
    {
        setDirection(getDirection() + Location.HALF_RIGHT);
    }
   ```
4. Why can the move method be called in the BoxBug class when there is no move method in the BoxBug code?  
答：因为`move`方法在`Bug`类中实现了，并且属性为`public`，而`BoxBug`类继承了`Bug`类，所以可以直接调用其中的`public`方法。  
5. After a BoxBug is constructed, will the size of its square pattern always be the same? Why or why not?  
答："正方形"模式大小会一直一样。尽管可能会因为初始化的位置和边界大小的不合适使`BugRunner`在移动过程遇到`Grid`的边界使得最终的"正方形"的大小与设置的正方形不符合，但是其大小一经初始化便不会改变。    
6. Can the path a BoxBug travels ever change? Why or why not?  
答：如果只在Grid中创建一个`BoxBug`，那么它的移动路线是不会改变的（除非在初始化它的位置和边界大小时让其移动会碰到边界）。而如果在其运动范围内出现了其他实体与其相遇那么它的移动路线可能会改变（具体为move方法和canMove方法的内容）
7. When will the value of steps be zero?  
答：  
- 初始化一个`BoxBug`
- 当`BoxBug`到达规定的移动边界时（即：`steps == sideLength`）
- canMove()方法返回false时
    - 当`BoxBug`遇到另外一个`Actor`时（`Flower`除外）
    - `BoxBug`移动的下一个位置不合法(超过`Grid`边界)时（此处为`canMove`方法中对`gr.isValid(next)`的判断，`gr`为一个`Grid<Actor>`，`next`为下一个`Location`）
    - `gr`为空，即Grid不存在时（此时应该连`BoxBug`都不能实例化吧？）