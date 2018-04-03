package org.xiami.tetris.model;

import java.util.Random;

import org.xiami.tetris.listeners.BlockListenler;


/**
 * CopyRight &copy; 2014 <a href="http://blog.csdn.net/shouhouxinling">虾米</a>. All Rights Reserved.<br>
 * 网址: <a href="http://blog.csdn.net/shouhouxinling">http://blog.csdn.net/shouhouxinling</a> <br>
 * 项目名称：tetris<br>
 * 文件名：BlockFactory.java<br>
 * 类名称：BlockFactory<br>
 * 
 * 描述：方块制造工厂
 * @author 夏永生
 * @datetime 2014年2月23日/下午11:59:00
 * @version 1.0.0
 */
public final class BlockFactory {
	
	
	/**
	 * 5种方块的形状
	 * blockarr
	 */
	private static final int blockarr [][][] = {
		{{0,1,0,0},{1,1,1,0},{0,0,0,0},{0,0,0,0}}, // 两行， 1,3 第一行的方块在中间 
		{{1,1,0,0},{1,1,0,0},{0,0,0,0},{0,0,0,0}}, // 方块,  2,2
		{{1,0,0,0},{1,1,1,0},{0,0,0,0},{0,0,0,0}}, // 两行， 1,3第一行的方块在第一个
		{{0,0,1,0},{1,1,1,0},{0,0,0,0},{0,0,0,0}}, // 两行， 1,3第一行的方块在第三个
		{{1,0,0,0},{1,1,0,0},{0,1,0,0},{0,0,0,0}}, // 三行，1,2,1第一行的方块在第一个，第三行的方块在第二个
		{{0,1,0,0},{1,1,0,0},{1,0,0,0},{0,0,0,0}}, // 三行，1,2,1第一行的方块在第二个，第三行的方块在第一个
		{{1,1,1,1},{0,0,0,0},{0,0,0,0},{0,0,0,0}}};// 长条 
	
	/**
	 * 方块制造工厂
	 * @return
	 */
	public static Block getBlock(BlockListenler l){
		Block b = new Block(blockarr[new Random().nextInt(7)]);
		// 注册方块事件监听器
		b.setBlockListenler(l);
		return b;
		
	}

}
