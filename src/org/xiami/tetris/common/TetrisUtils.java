package org.xiami.tetris.common;

/**
 * CopyRight &copy; 2014 <a href="http://blog.csdn.net/shouhouxinling">虾米</a>. All Rights Reserved.<br>
 * 网址: <a href="http://blog.csdn.net/shouhouxinling">http://blog.csdn.net/shouhouxinling</a> <br>
 * 项目名称：tetris<br>
 * 文件名：TetrisUtils.java<br>
 * 类名称：TetrisUtils<br>
 * 
 * 描述：俄罗斯方块的一些工具类
 * @author 夏永生
 * @datetime 2014年2月28日/下午9:20:27
 * @version 1.0.0
 */
public  final class TetrisUtils {
	/**
	 * 将方块中的数据copy 到地图中作为地图的一部分
	 * @param contener 地图容器
	 * @param block 方块
	 * @param top 当前方块离顶部位置
	 * @param left 当前方块离左边位置
	 * @return
	 */
	public static synchronized int [][]  arrcopy(int [][] contener,int [][] block,int top,int left){
		for (int i = 0; i < block.length; i++) {
			int[] js = block[i];
			for (int j = 0; j < js.length; j++) {
				int index = js[j];
				if(index==1){
					int x = j+left,y = i + top;
					contener[y][x] = index;
				}
			}
		}
		return contener;
	}
	
}
