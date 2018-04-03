package org.xiami.tetris.listeners;

import org.xiami.tetris.model.Block;

/**
 * CopyRight &copy; 2014 <a href="http://blog.csdn.net/shouhouxinling">虾米</a>. All Rights Reserved.<br>
 * 网址: <a href="http://blog.csdn.net/shouhouxinling">http://blog.csdn.net/shouhouxinling</a> <br>
 * 项目名称：tetris<br>
 * 文件名：BlockListenlers.java<br>
 * 类名称：BlockListenlers<br>
 * 描述：方块监听器
 * @author 夏永生
 * @datetime 2014年2月24日/下午9:42:47
 * @version 1.0.0
 */
public interface BlockListenler {
	
	/**
	 * 判断图形是否能够移动
	 * @param block
	 * @return
	 */
	boolean  isBlockMoveDown(Block block);
	
	/**
	 * 图形下移
	 * @param block
	 */
	public void moveDown(Block block);
}
