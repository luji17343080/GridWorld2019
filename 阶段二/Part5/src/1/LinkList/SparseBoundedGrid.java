package LinkList;

import info.gridworld.grid.Location;
import info.gridworld.grid.AbstractGrid;
import java.util.ArrayList;

/*链表实现Grid接口：实现其中的方法
 * 稀疏数组(sparseArray)存储链表节点：数组索引代表行的索引，大小代表Grid的行数
 * 继承AbstractGrid<E>类
 * 参考BoundedGrid类
 * 区别在于原来的类用二维数组存储，新类用链表存储节点
 * 重载getOccupiedLocations方法
 * 重载get、put、remove方法
 */
public class SparseBoundedGrid<E> extends AbstractGrid<E>
{	
	// Grid元素存储链表
	private SparseGridNode[] sparseArray;
	// 行和列
	private int cols;
	@SuppressWarnings("unused")
	private int rows;
	// 构造函数设置行和列
	public SparseBoundedGrid(int rows, int cols) {
		// 错误处理：行列小于等于0
		if (rows <= 0) {
		      throw new IllegalArgumentException("rows <= 0");
		}
		if (cols <= 0) {
			throw new IllegalArgumentException("cols <= 0");
		}
		// 稀疏数组大小为rows
		this.sparseArray = new SparseGridNode[rows];
		this.rows = rows;  
		this.cols = cols;
	}
	// 稀疏矩阵的大小为rows
	public int getNumRows() {
	  return sparseArray.length;
	}
	public int getNumCols() {
	  return cols;
	}
	// 判断一个位置是否在Grid内
	public boolean isValid(Location loc) {
		return 0 <= loc.getRow() && loc.getRow() < getNumRows()
				&& 0 <= loc.getCol() && loc.getCol() < getNumCols();
	}
	
	// 获得被objects占用的位置
	public ArrayList<Location> getOccupiedLocations() {
	  ArrayList<Location> occupiedLocations = new ArrayList<Location>();
	  
	  // 遍历稀疏数组所有元素
	  for (int r = 0; r < getNumRows(); r++) {
		  // 如果链表为空，则跳过
	      if (sparseArray[r] == null) { 
	          continue;
	      } else {
	    	  // 链表头节点
	          SparseGridNode node = sparseArray[r];
	          // 遍历链表中的所有节点
	          while (node != null) {
	              Location loc = new Location(r, node.getCol());
	              occupiedLocations.add(loc);
	              node = node.getNext();
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
	  // 获取当前行的链表
	  int r = loc.getRow(), c = loc.getCol();
	  SparseGridNode node = sparseArray[r];
	  while (node != null) {
	      if (node.getCol() == c) {
	          return (E) node.getOccupant();
	      }
	      node = node.getNext();
	  }
	  return null;
	}
	// 添加一个object到指定位置
	public E put(Location loc, E obj) {
		// 错误处理：位置 在界外或者 对象 不存在
		if (!isValid(loc)) { 
			throw new IllegalArgumentException("Location " + loc
	                          + " is not valid");
		}
		if (obj == null) {
			throw new NullPointerException("obj == null");
		}
		// 获取该位置以前的object：null 或者 occupant
		E oldOccupant = get(loc);
		int r = loc.getRow(), c = loc.getCol();
		// 获取该行的链表
		SparseGridNode node = sparseArray[r];
		// 在链表中添加新的节点:occupant为obj， 列索引为指定位置的col，指向初始节点node
		SparseGridNode node2 = new SparseGridNode(obj, c, node);
		// 头节点变为node2
		sparseArray[loc.getRow()] = node2;
		return  oldOccupant;
	}
	// 删除指定位置的节点，返回原来的object
	public E remove(Location loc) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location " + loc
				+ " is not valid");
		}
		// 得到原来位置上的object
		E oldOccupant = get(loc);
		// 获取指定位置的行数、列数信息
		int r = loc.getRow(), c = loc.getCol();
		// 获取该行的链表头节点
		SparseGridNode node = sparseArray[r];
	    // 如果链表为空，则直接返回
		if (node == null) {
			return oldOccupant;
		}
		// 如果头节点的列索引为指定位置的列数，则直接将链表头节点删除
		if (node.getCol() == c) {        			
			sparseArray[r] = node.getNext();			
			return oldOccupant;
		}
		// 指定位置节点的上一节点
		SparseGridNode nodePre = null;
		// 遍历链表中所有节点，直到找到列为指定位置的列数为止
		while (node != null) {
			if (node.getCol() == c) {        
				break;
			}
			nodePre = node;
			node = node.getNext();
		}
		// 如果头节点的列索引为指定位置的列数(指定位置节点的上一节点为空)，则直接将链表头节点删除
		if (nodePre == null) {        			
			sparseArray[r] = node.getNext();			
			return oldOccupant;
		}
		// 如果链表头节点不为指定位置，则将node节点的上一节点nodePre的next设置为node的next，并将node变为空
		nodePre.setNext(node.getNext());
		node = null;
		return oldOccupant;
	}
}
