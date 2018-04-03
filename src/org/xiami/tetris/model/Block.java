package org.xiami.tetris.model;

import java.awt.Color;
import java.awt.Graphics;

import org.xiami.tetris.listeners.BlockListenler;

import static org.xiami.tetris.common.Context.CELL_SIZE;

/**
 * CopyRight &copy; 2014 <a href="http://blog.csdn.net/shouhouxinling">虾米</a>. All Rights Reserved.<br>
 * 网址: <a href="http://blog.csdn.net/shouhouxinling">http://blog.csdn.net/shouhouxinling</a> <br>
 * 项目名称：Block<br>
 * 文件名：Block.java<br>
 * 类名称：Block<br>
 * 
 * 描述：方块
 * @author 夏永生
 * @datetime 2014年2月23日/下午11:15:16
 * @version 1.0.0
 */
public class Block {
	
	/**
	 * CopyRight &copy; 2014 <a href="http://blog.csdn.net/shouhouxinling">虾米</a>. All Rights Reserved.<br>
	 * 网址: <a href="http://blog.csdn.net/shouhouxinling">http://blog.csdn.net/shouhouxinling</a> <br>
	 * 项目名称：tetris<br>
	 * 文件名：Block.java<br>
	 * 类名称：Action<br>
	 * 
	 * 描述：方块的事件枚举
	 * @author 夏永生
	 * @datetime 2014年3月4日/下午11:22:00
	 * @version 1.0.0
	 */
	public enum Action{
		ROUND,
		LEFT,
		RIGHT,
		DOWN
	}
	
	
	private boolean pause = false;
	
	
	
	public void setPause(boolean pause) {
		this.pause = pause;
	}




	// 方块图像矩阵的二维数组
	private int [][] blockarr;
	
	// 当前状态 1 ~ 4  每次向右翻转
	
	private int status = 1;
	
	// 点阵y坐标
	private int top = 0;
	
	// 点阵x坐标
	private int left = 0; 
	
	// 方块的点阵宽度
	private int width = 0;
	
	// 方块的点阵宽度
	private int height = 0;
	
	private BlockListenler blockListenler;
	
	private Thread thread;
	
	/**
	 * 方块的构造方法，自动启动下移的方法 接收方块点阵后计算出方块的真实高度与真实宽度
	 */
	public Block(int [][] blockarr) {
		this.blockarr = blockarr;
		int x = 0 , y = 0; // x,y 分别代表当前方块占用空间的最大格数
		for (int i = 0; i < blockarr.length; i++) {
			int[] js = blockarr[i];
			boolean hasblock = false;
			for (int j = 0; j < js.length; j++) {
				int index = js[j];
				if(index==1){
					if(j>x)x=j;
					hasblock = true;
				}
			}
			if(hasblock){
				y ++;
			}
		}
		this.width = x+1;
		this.height = y;
		thread = new Thread(new BloakDown());
		thread.start();
	}
	

	/**
	 * 显示方块
	 * @param g
	 * @param blockarr 
	 */
	public void drawMe(Graphics g){
		for (int i = 0; i < blockarr.length; i++) {
			int [] cells = blockarr[i];
			for (int j = 0; j < cells.length; j++) {
				int index = cells[j];
				if(1==index){
					g.setColor(Color.RED);
					int x = (left+j)*CELL_SIZE,y = (top+i) * CELL_SIZE;
					g.fill3DRect(x, y, CELL_SIZE, CELL_SIZE, true);
				}
			}
		}
	}
	
	
	
	/**
	 * 左移
	 */
	public void moveLeft(){
		this.left--;
	}
	
	/**
	 * 右移
	 */
	public void moveRgiht(){
		this.left++;
	}
	
	/**
	 * 下移
	 */
	public void moveDown(){
		this.top++;
	}
	
	/**
	 * 翻转
	 */
	public void round(){
		this.status =  this.status++%4+1;
		// 执行一次高宽互换
		this.height = this.height + this.width;
		this.width = this.height - this.width;
		this.height = this.height - this.width;
		this.blockarr = arrround();
	}
	
	/**
	 * 矩阵翻转的算法实现
	 * @return
	 */
	public int[][] arrround(){
		int [][] arr = new int [this.blockarr.length][this.blockarr.length];
		// 实现所有方格90°翻转
		for (int i = 0; i < this.blockarr.length; i++) {
			int[] js = this.blockarr[i];
			for (int j = 0; j < js.length; j++) {
				arr[j][3-i] = js[j];
			}
		}
		int x_none = 0, y_none = 0; // x , y 轴的空行
		boolean x_first = true,y_first = true ;
		// 计算出 x,y 轴的空行数量
		for (int i = 0; i < arr.length; i++) {
			int[] js = arr[i];
			boolean xisnone = true ,yisnone = true;
			for (int j = 0; j < js.length; j++) {
				int index = js[j];
				if(index == 1){ //  行有方块
					xisnone = false;
				}
				int y_index = arr[j][i];
				if(y_index == 1){ //  列有方块
					yisnone = false;
				}
			}
			if(!xisnone && y_first ){ // 行不空并且第一次计算 ，空列数为当前的行值
					y_first = false;
					y_none = i;
			}
			if(!yisnone && x_first ){ // 列不空并且第一次计算 ，空行数为当前的行值
					x_first = false;
					x_none = i;
			}
		}
		//  左边所有空行移动到右边
		for (int i = 0; i < arr.length; i++) {
			int[] js = arr[i];
			for (int k = 0; k < x_none; k++) {
				int  tmp = arr[i][0];
				for (int j = 0; j < js.length-1; j++) {
					arr[i][j] =  arr[i][j+1];
				}
				arr[i][js.length-1] = tmp;
			}
		}
		//实现顶部所有空行移动到底部 
		for (int k = 0; k < y_none; k++) {
			int tmp [] = arr[0];
			for (int i = 0; i < arr.length -1; i++) {
				arr[arr.length -1] = arr[0];
			}
			arr[arr.length -1] = tmp;
		}
		
		return arr;
	}
	
	public void setBlockListenler(BlockListenler blockListenler) {
		this.blockListenler = blockListenler;
	}
	
	
	public int[][] getBlockarr() {
		return blockarr;
	}

	public int getStatus() {
		return status;
	}

	public int getTop() {
		return top;
	}

	public int getLeft() {
		return left;
	}
	

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}




	private class BloakDown implements Runnable {

		@Override
		public void run() {
			if(null==blockListenler){
				throw new RuntimeException("没有注册下落事件监听器");
			}
			while(blockListenler.isBlockMoveDown(Block.this)){
				
				if (pause) { // 判断暂停
					continue;
				}
				
				moveDown();
				/**
				 * 触发下落事件
				 */
				
				blockListenler.moveDown(Block.this);
				try {
					// 停止一秒钟后向下移动一格
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
}
