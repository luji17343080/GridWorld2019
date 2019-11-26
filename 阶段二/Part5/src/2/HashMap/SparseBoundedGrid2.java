package HashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import info.gridworld.grid.AbstractGrid;
import info.gridworld.grid.Location;

/* hashMap实现Grid接口：实现其中的方法
 * 用Map存储Grid中的节点
 * 继承AbstractGrid<E>类
 * 参考BoundedGrid类
 * 区别在于原来的类用二维数组存储，新类HashMap存储节点
 * 重载getOccupiedLocations方法
 * 重载get、put、remove方法
 */
public class SparseBoundedGrid2<E> extends AbstractGrid<E> {
	// HashMap存储被占用的位置
    private Map<Location, E> occupantMap;
    // 行和列
    private int rows;
    private int cols;
    // 构造函数设置行数和列数
    public SparseBoundedGrid2(int rows, int cols) {
    	// 错误处理：行列小于等于0
        if (rows <= 0) {
            throw new IllegalArgumentException("rows <= 0");
        }
        if (cols <= 0) {
            throw new IllegalArgumentException("cols <= 0");
        }
        this.rows = rows;
        this.cols = cols;
        occupantMap = new HashMap<Location, E>();
    }
    

    public int getNumRows() {
        return rows;
    }
    public int getNumCols() {
        return cols;
    }
    // 判断一个位置是否在界内
    public boolean isValid(Location loc) {
    	return 0 <= loc.getRow() && loc.getRow() < getNumRows()
				&& 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }
    
    // 获得被objects占用的位置
    public ArrayList<Location> getOccupiedLocations() {
        ArrayList<Location> occupiedLocations = new ArrayList<Location>();
        // 直接遍历占用图中的键集，将其中的位置加入到Location数组列表中
        for (Location loc : occupantMap.keySet()) {
        	occupiedLocations.add(loc);
        }
        return occupiedLocations;
    }
    // 获得某一个位置的object
    public E get(Location loc) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        // 直接调用Map中的函数get获取某个键的Value
        return occupantMap.get(loc);
    }
    // 将object放入指定位置
    public E put(Location loc, E obj) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        if (obj == null) {
            throw new NullPointerException("obj == null");
        }
        // 直接调用map中的put函数
        return occupantMap.put(loc, obj);
    }
    // 将某个位置的object删除
    public E remove(Location loc) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        // 直接调用Map中的remove函数删除loc位置的元素
        return occupantMap.remove(loc);
    }
}

