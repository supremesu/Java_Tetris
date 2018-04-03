package org.xiami.tetris.ui;


import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.xiami.tetris.model.Block;
import org.xiami.tetris.model.Ground;

import static org.xiami.tetris.common.Context.CELL;
import static org.xiami.tetris.common.Context.ROW;
import static org.xiami.tetris.common.Context.CELL_SIZE;

/**
 * CopyRight &copy; 2014 <a href="http://blog.csdn.net/shouhouxinling">虾米</a>. All Rights Reserved.<br>
 * 网址: <a href="http://blog.csdn.net/shouhouxinling">http://blog.csdn.net/shouhouxinling</a> <br>
 * 项目名称：Block<br>
 * 文件名：GamePanel.java<br>
 * 类名称：GamePanel<br>
 * 
 * 描述：游戏的主窗口
 * @author 夏永生
 * @datetime 2014年2月23日/下午11:01:02
 * @version 1.0.0
 */
public class GamePanel extends JPanel {
	
	
	
	private Image _img;
	
	private Graphics _g;
	
	
	
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 648652937201604698L;

	public GamePanel() {
		int width = CELL_SIZE * CELL,height = CELL_SIZE * ROW;
		setSize(width,height);
		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		setFocusable(true);
	}

	/**
	 * 将地图与方块画到画板上
	 * @param ground
	 * @param block
	 */
	public synchronized void redispay(Ground ground, Block block) {
		if(null==_img ){
			_img = createImage(getWidth(), getHeight());
			if(null==_g){
				_g = _img.getGraphics();
			}
		}
		if(null!=_g){
			_g.setColor(getBackground());
			_g.fillRect(0, 0, getWidth(), getHeight());
			if(null!=ground)
			ground.drawMe(_g);
			if(null!=block)
			block.drawMe(_g);
			paint(this.getGraphics());
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(_img, 0,0, this);
	}

}
