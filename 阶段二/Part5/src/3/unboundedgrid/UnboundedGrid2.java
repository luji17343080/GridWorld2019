package unboundedgrid;

import info.gridworld.grid.Location;
import info.gridworld.grid.AbstractGrid;
import java.util.ArrayList;
/* 重新实现UnboundedGrid类
 * 用二维数组存储被占用的位置
 * 继承AbstractGrid<E>类
 * 参考UnboundedGrid类
 * 区别在于原来的类用Map存储，现在的类用二维数组存储
 * 重载getOccupiedLocations方法
 * 重载get、put、remove方法
 */
public class UnboundedGrid2<E> extends AbstractGrid<E> 
{
	// 初始magic number
	private final static int initNum = 16;
	// 占用对象数组
	private Object[][] occupantArray;
	// 正方形边长
	private int length;
	// provide a constructor
	public UnboundedGrid2() 
	{
		length = initNum;
		occupantArray = new Object[initNum][initNum];
	}
	// 无边界
	public int getNumRows() 
	{
		return -1;
	}
	// 无边界
	public int getNumCols() 
	{
		return -1;
	}
	// 判断一个位置是否在界内
	public boolean isValid(Location loc) 
	{
		return (loc.getRow() >= 0 && loc.getCol() >= 0);
	}
	// 重新设置occupantArray的大小
	public void resize(int newLength) {
		Object[][] newArray = new Object[newLength][newLength];
		for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.length; j++) {
                newArray[i][j] = occupantArray[i][j];
            }
        }
		occupantArray = newArray;
		this.length = newLength;
	}
	// 获得被占用的位置
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> occupiedLocations = new ArrayList<Location>();
        // 遍历occupantArray的所有位置
        for (int r = 0; r < length; r++) {
            for (int c = 0; c < length; c++) {
                Location loc = new Location(r, c);
                if (occupantArray[r][c] != null) {
                	occupiedLocations.add(loc);
                }
            }
        }
        return occupiedLocations;
    }
	// 获得某一个位置的object
	public E get(Location loc) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location " + loc
					+ " is not valid");
		}
		if (loc.getRow() >= length || loc.getCol() >= length) {
			return null;
		}
		return (E) occupantArray[loc.getRow()][loc.getCol()]; 
	}

	// 将object放入指定的位置
	public E put(Location loc, E obj) {
		// 错误处理：位置不在界内或者object为空
		if (!isValid(loc)) {
			throw new NullPointerException("loc == null");
		}
		if (obj == null) {
			throw new NullPointerException("obj == null");
		}
		// 如果设置的row和col大于了16，则将正方形长度翻倍
		int r = loc.getRow(), c = loc.getCol();
		int newLength = length;
		while (r > length || c > length){
			newLength *= 2;
		}
		if (newLength != length) {
			resize(newLength);
		}
		// 将object占用数组指定位置改为obj
		E oldOccupant = get(loc);
		// 注意此时的getRow()...可能和上面的r不一致
		occupantArray[loc.getRow()][loc.getCol()] = obj;
		return oldOccupant;
	}
	// 删除指定位置的object，并返回object
	public E remove(Location loc) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location " + loc
					+ " is not valid");
		}
		E r = get(loc);
		// 直接让指定位置的的占用数组为空
		occupantArray[loc.getRow()][loc.getCol()] = null;
		return r;
	}
}
