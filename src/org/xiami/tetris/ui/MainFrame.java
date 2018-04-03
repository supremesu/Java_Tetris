package org.xiami.tetris.ui;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.xiami.tetris.controller.Controller;
import org.xiami.tetris.model.Ground;
import org.xiami.tetris.model.Ground.GameStatus;

/**
 * CopyRight &copy; 2014 <a href="http://blog.csdn.net/shouhouxinling">虾米</a>. All Rights Reserved.<br>
 * 网址: <a href="http://blog.csdn.net/shouhouxinling">http://blog.csdn.net/shouhouxinling</a> <br>
 * 项目名称：tetris<br>
 * 文件名：MainFrame.java<br>
 * 类名称：MainFrame<br>
 * 
 * 描述：搞一个可以操作的界面吧
 * @author 夏永生
 * @datetime 2014年3月15日
 * @version 1.0.0
 */
public class MainFrame extends JFrame implements ActionListener {
	
	private JPanel controlpanel;// 操作面板
	
	private JButton btn_start; // 开始按钮
	
	private JButton btn_pause; // 暂停按钮
	
	private JButton btn_stop;	// 停止按钮
	
	private Ground ground; // 地图对象
	
	private GamePanel gamePanel; // 游戏面板
	
	private Controller controller; // 游戏控制器
	
	private JLabel lbl_count; // 行数显示
	
	private static final String START_COMMAND = "start"; // 开始
	private static final String PAUSE_COMMAND = "pause";// 暂停
	private static final String STOP_COMMAND = "stop";// 停止
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2079763704569854554L;

	public MainFrame(String title) throws HeadlessException {
		super(title);
		init();
		
	}
	
	private void init(){
		
		gamePanel = new GamePanel();
		controlpanel = new JPanel();
		btn_start = new JButton("开始");
		btn_pause = new JButton("暂停");
		btn_stop = new JButton("停止");
		lbl_count = new JLabel("已消除:(0)行");
		
		btn_start.setActionCommand(START_COMMAND);
		btn_pause.setActionCommand(PAUSE_COMMAND);
		btn_stop.setActionCommand(STOP_COMMAND);
		btn_pause.setEnabled(false);
		btn_stop.setEnabled(false);
		
		controlpanel.setPreferredSize(new Dimension(100,gamePanel.getHeight()));// 这次控制没有效果
		controlpanel.setMaximumSize(new Dimension(100,gamePanel.getHeight())); // 这样才行？？ why
		controlpanel.setLayout(new BoxLayout(controlpanel, BoxLayout.Y_AXIS));
		controlpanel.add(Box.createVerticalStrut(30));
		controlpanel.add(btn_start);
		controlpanel.add(Box.createVerticalStrut(40));
		controlpanel.add(btn_pause);
		controlpanel.add(Box.createVerticalStrut(40));
		controlpanel.add(btn_stop);
		controlpanel.add(Box.createVerticalStrut(40));
		controlpanel.add(lbl_count);
		controlpanel.add(Box.createVerticalStrut(20));
		
		ground = new Ground();
		controller = new Controller(ground, gamePanel);
		controller.setLableCount(lbl_count);
		int width = gamePanel.getWidth() + 135;
		int height = gamePanel.getHeight() + 35;
		setSize(width, height);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		getContentPane().add(Box.createHorizontalStrut(20));
		getContentPane().add(controlpanel);
		getContentPane().add(gamePanel);
		
		addKeyListener(controller);
		btn_start.addActionListener(this);
		btn_pause.addActionListener(this);
		btn_stop.addActionListener(this);
		
		setFocusable(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		gamePanel.redispay(ground, null);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) { // 按钮事件
		String command = e.getActionCommand();
		if(START_COMMAND==command){ // 开始按钮
			controller.startGame();
			btn_start.setEnabled(false);
			btn_pause.setEnabled(true);
			btn_stop.setEnabled(true);
		}else if(PAUSE_COMMAND==command){ // 暂停按钮
			
			controller.pause();
			btn_start.setEnabled(true);
			btn_pause.setEnabled(false);
			btn_stop.setEnabled(true);
			
		}else if(STOP_COMMAND==command){ // 停止按钮
			ground.setStatus(GameStatus.STOP);
			controller.gameDie(ground);
			btn_start.setEnabled(true);
			btn_pause.setEnabled(false);
			btn_stop.setEnabled(false);
		}
	}
	
	
}
