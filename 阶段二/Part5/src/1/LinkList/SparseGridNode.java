package LinkList;

// 行链表节点类
public class SparseGridNode
{
	// 占用网格的对象
	private Object occupant;
	// 列索引
	private int col;
	// 下一个节点索引
	private SparseGridNode next;
	// 构造函数
	public SparseGridNode(Object occupant, int col, SparseGridNode next) {
		this.occupant = occupant;
		this.col = col;
		this.next = next;
	}
	
	// 设置occupant
	public void setOccupant(Object occupant) {
			this.occupant = occupant;
	}
	public Object getOccupant() {
		return occupant;  
	}
	  
	// 设置列索引 
	public void setCol(int col) {
		this.col = col;
	}
	public int getCol() {
		return col;
	}
	
	// 设置下一节点
	public void setNext(SparseGridNode next) {
		this.next = next;
	}
	public SparseGridNode getNext() {
		return next;
	}
}
