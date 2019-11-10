# Part 1问题回答  
## Step1：Running the Demo
1. Does the bug always move to a new location? Explain.  
答：不会，当bug遇到Actor（魔鬼？）、rock、另外的bug或者边界时会改变方向。而当bug遇到原来走过的地方（花）的时候不会改变方向，也就是说不会一直移动到新的位置。  
2. In which direction does the bug move?  
答：bug总是往头朝的方向移动，可以八个方向（东南西北...），当bug遇到actor、rock、另外的bug或者边界时会自动改变方向（45°）。  
3. What does the bug do if it does not move?  
答：当遇到障碍不能移动时，bug会自动顺时针旋转45°改变头的朝向，直到找到一个新的没有障碍的方向（空白或者Flower）继续运动。  
4. What does a bug leave behind when it moves?  
答：会留下一朵Flower（与bug的颜色一样），而随着bug的移动，Flower的颜色会逐渐加深（路程越远颜色越深），直到bug再次移动到Flower的位置，Flower的颜色才会重新变得与bug一样。  
5. What happens when the bug is at an edge of the grid? (Consider whether the bug is facing the edge as well as whether the bug is facing some other direction when answering this question.)  
答：当bug没有面向边缘时，正常运动；当bug面向边缘时，bug会顺时针转动（每次45°）调整方向，直到面朝空白的网格或者Flower时继续向前运动；若转动过程中没遇到空白或者Flower（即全是障碍，由于来时的路径上一定有Flower，所以此现象只能手动调）则将自身转变为一朵Flower（颜色不变）。  
6. What happens when a bug has a rock in the location immediately in front of it?  
答：bug顺时针旋转（每次45°）调整方向直到面朝空白的网格或者Flower时，继续向前运动；若转动过程中没遇到空白或者Flower（即全是障碍，由于来时的路径上一定有Flower，所以此现象只能手动调）则将自身转变为一朵Flower（颜色不变）。  
7. Does a flower move?  
答：不能。  
8. What behavior does a flower have?  
答：当bug走过一个空白网格时，空白网格会出现一朵颜色与bug相同的Flower，并且随着bug的移动，Flower的颜色会逐渐加深（路程越远颜色越深）；而当bug经过一朵Flower时，Flower的颜色会重新变得与bug相同。  
9. Does a rock move or have any other behavior?  
答：rock不能move，并且没有其他任何行为，只能阻碍bug的移动或者调用方法改变其位置、颜色和方向。  
10. Can more than one actor (bug, flower, rock) be in the same location in the grid at the same time?  
答：不能。
  
## Step2：Exploring Actor State and Behavior  
By clicking on a cell containing a bug, flower, or rock, do the following.
1.Test the setDirection method with the following inputs and complete the table, giving the compass direction each input represents.  

答：

|  Degrees    | Compass Direction    |
|  ----      | ----      |
| 0  | North |
| 45  | Northeast |
| 90  | East |
| 135  | Southeast |
| 180  | South |
| 225  | Southwest |
| 270  | West |
| 315  | Northwest |
| 360  | North |
 
2.Move a bug to a different location using the moveTo method. In which directions can you move it? How far can you move it? What happens if you try to move the bug outside the grid?  
答：可以让bug向grid中的任何一个位置移动，并且可以无视障碍物（Actor、rock以及bug），但是移动后bug面朝的方向不变；移动距离为当前位置到grid内的任何位置的距离；当bug要移出grid时，屏幕上会弹出报错信息。    
3.Change the color of a bug, a flower, and a rock. Which method did you use?  
答：setColor。  
4.Move a rock on top of a bug and then move the rock again. What happened to the bug?  
答：当rock移到bug上时，bug会消失，当rock移开时，bug不会重新出现。  