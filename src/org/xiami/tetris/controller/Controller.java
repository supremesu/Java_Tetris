package org.xiami.tetris.controller;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



import javax.swing.JLabel;

import org.xiami.tetris.listeners.BlockListenler;
import org.xiami.tetris.listeners.GroundListenler;
import org.xiami.tetris.model.Block;
import org.xiami.tetris.model.Block.Action;
import org.xiami.tetris.model.BlockFactory;
import org.xiami.tetris.model.Ground;
import org.xiami.tetris.model.Ground.GameStatus;
import org.xiami.tetris.ui.GamePanel;


/**
 * CopyRight &copy; 2014 <a href="http://blog.csdn.net/shouhouxinling">虾米</a>. All Rights Reserved.<br>
 * 网址: <a href="http://blog.csdn.net/shouhouxinling">http://blog.csdn.net/shouhouxinling</a> <br>
 * 项目名称：Block<br>
 * 文件名：Controller.java<br>
 * 类名称：Controller<br>
 * 
 * 描述：控制器
 * @author 夏永生
 * @datetime 2014年2月23日/下午11:17:10
 * @version 1.0.0
 */
public class Controller extends KeyAdapter implements BlockListenler ,GroundListenler {
	
	private Block block;// 方块
	 
	private Ground ground; // 地图
	
	private GamePanel gamePanel; // 游戏面板
	
	private int count = 0;
	
	private JLabel lbl_count; // 显示行数用的标签
	
	public Controller(Ground ground, GamePanel gamePanel) {
		super();
		this.ground = ground;
		this.gamePanel = gamePanel;
	}


	public void setLableCount(JLabel lbl_count){
		this.lbl_count = lbl_count;
	}
	
	/**
	 * 游戏开始
	 */
	public void startGame(){
		
		// 设置面板接受键盘事件
		gamePanel.requestFocus(false);
		// 修改游戏的状态未开始游戏
		if(GameStatus.STOP==ground.getStatus()){
			this.block = BlockFactory.getBlock(this);
			// 注册游戏面板的事件监听器
			gamePanel.addKeyListener(this);
			count = 0;
		}
		this.block.setPause(false);
		ground.setStatus(GameStatus.PAY);
		ground.setListener(this);
		
		// 重置一次画布
		gamePanel.redispay(ground,this.block);
		showCount();
		
	}

	/**
	 * 判断方块可否下落
	 */
	public synchronized boolean isBlockMoveDown(Block block) {
		if(this.block == block){
			if(ground.getStatus()==GameStatus.STOP){
				return false;
			}
			
			if(null==block){
				return true;
			}
			boolean canmove = ground.isMoveAble(block, Action.DOWN);
			if(canmove)return canmove;
			if(!canmove&&!ground.isDie()){
				
				ground.receive(block);
				this.block = BlockFactory.getBlock(this);
				ground.dellfullLine();
			}
			return canmove;
		}
		return false;
	}


	/**
	 * 方块的下落事件监听
	 */
	@Override
	public void moveDown(Block block) {
		if(null!=block&&null!=ground){
			gamePanel.redispay(ground, block);
		}
	}


	/**
	 * 键盘事件监听
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			if(ground.isMoveAble(block,Action.ROUND ))
				block.round();
			break;
		case KeyEvent.VK_DOWN:
			if(ground.isMoveAble(block,Action.DOWN ))
				block.moveDown();
			break;
		case KeyEvent.VK_LEFT:
			if(ground.isMoveAble(block,Action.LEFT ))
				block.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			if(ground.isMoveAble(block,Action.RIGHT ))
				block.moveRgiht();
			break;
		}
		gamePanel.redispay(ground, block);
	}


	/**
	 * 监听游戏挂掉的事件，清空地图
	 */
	@Override
	public void gameDie(Ground ground) {
		//  1.	 清空地图
		ground.clear();
		gamePanel.redispay(ground, null);
	}


	/**
	 * 暂停
	 */
	public void pause() {
		ground.setStatus(GameStatus.PAUSE);
		block.setPause(true);
	}


	/**
	 * 删行监听
	 */
	public void removeListener(Ground ground) {
		count++;
		showCount();
	}


	private void showCount(){
		if(null!=this.lbl_count){
			StringBuilder sb = new StringBuilder("已消除:(");
			sb.append(count).append(")行");
			this.lbl_count.setText(sb.toString());
		}
	}
	
	
	
	
	

}
