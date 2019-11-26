## Jumper设计文档  
`说明`：下面提到的`turn`都是指每次顺时针旋转45°；`jump`是指隔一个`cell`移动  
a. What will a jumper do if the location in front of it is empty, but the location two cells in front contains a flower or a rock?  
答：当一个jumper的相邻位置为空，隔位为`flower`时，`Jumper`会覆盖`flower`；当隔位为`rock`时，`Jumper`会`turn`以寻找下一个可`Jump`的位置。    
  
b. What will a jumper do if the location two cells in front of the jumper is out of the grid?  
答：会直接`turn`以寻找下一个`jump`的位置  
  
c. What will a jumper do if it is facing an edge of the grid?  
答：会`turn`以寻找下一个可`jump`的位置  
  
d. What will a jumper do if another actor (not a flower or a rock) is in the cell that is two cells in front of the jumper?  
答：先对下一步运动进行判定，若下一步运动两个`actor`能错开（move），则按照既定的路线移动；若另外一个`actor`此时在`turn`，则`Jumper`也`turn`以寻找下一个可移动的位置。  
  
e. What will a jumper do if it encounters another jumper in its path?  
答：如果另外一个`jumper`在`turn`，则该`jumper`也要`turn`；如果两个`jumper`下一步都可以`jump`，其中一个`jumper`会`turn`，另外一个`jumper`会`jump`：根据测试，`jump`的优先级由移动方向确定：右 > 下 > 左 > 上，即如果在一个`jumper`的运动路线上会碰到另外一个`jumper`，则会根据它们移动方向的优先级确定下一步的动作是`jump`还是`turn`，优先级高的`jump`。  

  
f. Are there any other tests the jumper needs to make?  
答：当一个`jumper`的运动路线上有`bug`时，`bug`的优先级高，即`bug`会`move`，而`jumper`会`turn`。除非`jumper`下一步只能`turn`，`bug`才会`turn`。