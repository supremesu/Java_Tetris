package org.xiami.tetris.model;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import org.xiami.tetris.common.Context;
import org.xiami.tetris.common.TetrisUtils;
import org.xiami.tetris.listeners.GroundListenler;
import org.xiami.tetris.model.Block.Action;

import static org.xiami.tetris.common.Context.CELL_SIZE;
import static org.xiami.tetris.common.Context.CELL;
import static org.xiami.tetris.common.Context.ROW;


/**
 * CopyRight &copy; 2014 <a href="http://blog.csdn.net/shouhouxinling">虾米</a>. All Rights Reserved.<br>
 * 网址: <a href="http://blog.csdn.net/shouhouxinling">http://blog.csdn.net/shouhouxinling</a> <br>
 * 项目名称：Block<br>
 * 文件名：Ground.java<br>
 * 类名称：Ground<br>
 * 
 * 描述：游戏地图
 * @author 夏永生
 * @datetime 2014年2月23日/下午11:16:08
 * @version 1.0.0
 */
public class Ground {
	
	/**
	 * 游戏状态
	 */
	public enum GameStatus {
		/**
		 *  停止
		 */
		STOP, 
		/**
		 *  游戏中
		 */
		PAY,  
		/**
		 *  暂停
		 */
		PAUSE
	}

	private GameStatus status = GameStatus.STOP; // 
	
	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}
	
	
	private int [][] contener = new int [ROW][CELL];
	
	/**
	 * 地图显示方法
	 * @param g
	 */
	public void drawMe(Graphics g){
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < CELL; j++) {
				g.setColor(Color.LIGHT_GRAY);
				int x = j*CELL_SIZE;
				int y = i*CELL_SIZE;
				if(0==contener[i][j]){
					g.draw3DRect(x, y,CELL_SIZE, CELL_SIZE, true);
				}else if(1==contener[i][j]){
					g.setColor(Color.GREEN);
					g.fill3DRect(x, y,CELL_SIZE, CELL_SIZE, true);
				}
			}
		}
	}
	
	/**
	 * 接收不可移动的方块，并作为地图的一部分
	 * @param b
	 */
	public synchronized void receive(Block b){
		contener = TetrisUtils.arrcopy(contener, b.getBlockarr(), b.getTop(), b.getLeft());
	}
	
	
	/**
	 * 地图的事件监听 
	 */
	private GroundListenler listener;
	
	
	public void setListener(GroundListenler listener) {
		this.listener = listener;
	}

	/**
	 * 方块是否能够在地图上移动或者翻转
	 * @param block
	 * @param action
	 * @return
	 */
	public synchronized boolean isMoveAble(Block block,Action action){
		boolean moveable = false;
		int left = block.getLeft();
		int top = block.getTop();
		int width = block.getWidth();
		int height = block.getHeight();
		int arr[][] = block.getBlockarr();
		
		switch (action) {
		case ROUND: // 变形实际上是做一次高宽交换
			width = width + height;
			height = width - height;
			width = width - height;
			arr = block.arrround();
			break;
		case LEFT:
			left--;
			break;
		case RIGHT:
			left++;
			break;
		case DOWN:
			top++;
			break;
		}
		int x = left+width,y = top+height;
		if(0 <= left && x <= Context.CELL){
			if(top >= 0 && y<= Context.ROW){
				moveable = true;
			}
		}
		
		if(moveable){
			// 判断下一步的方块位置是否有障碍物
			end:
				for (int i = 0; i < arr.length; i++) {
					int [] row = arr[i];
					for (int j = 0; j < row.length; j++) {
						int idx = row[j];
						if(1==idx){
							if(contener[top+i][left+j]==1){
								moveable = false;
								break end;
							}
						}
					}
				}
		}
		
		return moveable;
	}
	
	/**
	 * 删除满行
	 */
	public synchronized void dellfullLine(){
		
		// 查出所有满行
		int [] key = new int [Context.ROW];
		for (int i = 0; i < this.contener.length; i++) {
			int index = Context.ROW - i -1;
			int [] arr = this.contener[index];
			int len = 0;
			for (int j = 0; j < arr.length; j++) {
				if(1==arr[j])len++;
			}
			if(len == arr.length){
				key[index]= 1;
			}
		}
		
		// 删除所有的满行
		int index = 0;
		for (int i = key.length - 1; i > 0 ; i--) {
			if(key[i]==0)continue;
			for (int j = i+index; j > 0; j--) {
				if(j>1){
					this.contener[j] = this.contener[j-1];
				}else{
					this.contener[j] = new int [Context.CELL];
				}
			}
			index++;
			listener.removeListener(this);
		}
		
	}
	
	/**
	 * @return
	 *  判断游戏是否死亡
	 */
	public synchronized boolean isDie(){
		int [] first = this.contener[0];
		boolean die = false;
		for (int i : first) {
			if(1==i){
				die = true;
			}
		}
		if(die){
			setStatus(GameStatus.STOP);
			if(null==listener){
				throw new RuntimeException("必须设置地图的监听器");
			}
			listener.gameDie(this);
			JOptionPane.showMessageDialog(null, "你挂了！");
		}
		return die;
	}

	
	public void clear() {
		this.contener = new int [ROW][CELL];
	}

}
