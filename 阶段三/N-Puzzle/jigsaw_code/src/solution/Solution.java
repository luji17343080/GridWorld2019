package solution;

import java.io.IOException;
import java.util.Vector;

import jigsaw.Jigsaw;
import jigsaw.JigsawNode;


/**
 * 在此类中填充算法，完成重拼图游戏（N-数码问题）
 */
public class Solution extends Jigsaw {
	private Vector<JigsawNode> openList;	// open表 ：用以保存已发现但未访问的节点
	private Vector<JigsawNode> closeList;	// close表：用以保存已访问的节点
	private int searchedNodesNum; // 搜索节点的数目
    /**
     * 拼图构造函数
     */
    public Solution() {
    	beginJNode = null;
        endJNode = null;
        currentJNode = null;
        this.openList = new Vector<JigsawNode>();
        this.closeList = new Vector<JigsawNode>();
        searchedNodesNum = 0;
    }

    /**
     * 拼图构造函数
     * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
     */
    public Solution(JigsawNode bNode, JigsawNode eNode) {
        super(bNode, eNode);
        this.openList = null;
        this.closeList = null;
    }
	
    /**
     *（实验一）广度优先搜索算法，求指定5*5拼图（24-数码问题）的最优解
     * 填充此函数，可在Solution类中添加其他函数，属性
     * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
     * @return 搜索成功时为true,失败为false
     * @throws IOException 
     */
    public boolean BFSearch(JigsawNode bNode, JigsawNode eNode) {
    	// 用以存放某一节点的邻接节点
    	Vector<JigsawNode> adjacentJNodes = new Vector<JigsawNode>();
    	setBeginJNode(bNode);
    	setEndJNode(eNode);
    	// 将起始节点插入openList中
    	this.openList.addElement(beginJNode);		
    	// 如果openList为空，则搜索失败，问题无解;否则循环直到求解成功
    	while (this.openList.isEmpty() != true) {
    		// a. 访问openList中的第一个节点v，将其设置为currentJNode，若v为目标节点，则搜索成功，设置完成标记isCompleted为true，找到解路径，退出。
    		currentJNode = this.openList.elementAt(0);
    		searchedNodesNum++;
    		// 记录并显示搜索过程
    		//System.out.println("Searching.....Number of searched nodes:" + this.closeList.size() + "   Current state:" + this.currentJNode.toString());	
    		if (currentJNode.equals(endJNode)){
    			this.getPath(); // 修改slutionPath
    			break;
   			}
    				
  			// b. 从openList中删除节点v,并将其放入closeList中，同时将搜索节点数加1			
    		this.openList.removeElementAt(0);
    		this.closeList.addElement(currentJNode);

    		// 得到某一节点的所有相邻节点（上下左右）
    		JigsawNode tempJNode2;
    		for(int i = 0; i < 4; i++){
    			tempJNode2 = new JigsawNode(currentJNode);
    			if(tempJNode2.move(i) && !this.closeList.contains(tempJNode2) && !this.openList.contains(tempJNode2)) {
    				adjacentJNodes.addElement(tempJNode2);
    			}
    		}
    		// c. 将所有与v邻接且未曾被访问的节点插入openList中
    		while (!adjacentJNodes.isEmpty()) {
    			this.openList.addElement(adjacentJNodes.elementAt(0));
    			adjacentJNodes.removeElementAt(0);
    		}
    	}
    	System.out.println("Jigsaw BFSearch Result:");
        System.out.println("Begin state:" + getBeginJNode().toString());
        System.out.println("End state:" + getEndJNode().toString());
        System.out.println("Solution Path: ");
        System.out.println(getSolutionPath());
        System.out.println("Total number of searched nodes:" + getSearchedNodesNum());
        System.out.println("Depth of the current node is:" + getCurrentJNode().getNodeDepth());
		return this.isCompleted();		
    }


    /**
     *（Demo+实验二）计算并修改状态节点jNode的代价估计值:f(n)
     * 如 f(n) = s(n). s(n)代表后续节点不正确的数码个数
     * 此函数会改变该节点的estimatedValue属性值
     * 修改此函数，可在Solution类中添加其他函数，属性
     * @param jNode - 要计算代价估计值的节点
     */
    @Override
    public void estimateValue(JigsawNode jNode) {
		// 后续节点不正确的数码个数
		int s = 0; 
		
		// 拼图的维数
		int dimension = JigsawNode.getDimension();
		
		for (int index = 1; index < dimension * dimension; index++) {
			// 计算水平方向后续节点不正确的数码个数
			if (jNode.getNodesState()[index] + 1 != jNode.getNodesState()[index + 1]) {
				s++;
			}
		}
		
		for (int index = 1; index < dimension * (dimension - 1); index++) {
			// 计算垂直方向后续节点不正确的数码个数
			if (jNode.getNodesState()[index] + dimension != jNode
					.getNodesState()[index + dimension]) {
				s++;
			}
		}
		// 所有节点与其正确位置的欧氏距离之和
		int distance = 0;
		for (int i = 1; i <= dimension * dimension; i++) {
			// 计算欧氏距离
			if (jNode.getNodesState()[i] != i && jNode.getNodesState()[i] != 0) {
				// 正确位置行数
				int rightRow = (jNode.getNodesState()[i] - 1) / dimension;
				// 正确位置列数
				int rightCol = (jNode.getNodesState()[i] - 1) % dimension;
				// 当前行数
				int nowRow = (i - 1) / dimension;
				// 当前列数
				int nowCol = (i - 1) % dimension;
				// 直线距离
				distance += Math.abs(rightRow - nowRow) + Math.abs(rightCol - nowCol);
			}
		}	
		
		// 所有放错位的数码的个数
		int n = 0;
		for (int i = 1; i <= dimension * dimension; i++) {
			// 放错位的数码的个数总和
			if (jNode.getNodesState()[i] != i && jNode.getNodesState()[i] != 0) {
				n++;
			}
		}
		/* 启发式函数估计值 */
		jNode.setEstimatedValue(s*6 + distance*10 + n + jNode.getNodeDepth());
	}
}