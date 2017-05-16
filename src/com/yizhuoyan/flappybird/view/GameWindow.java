package com.yizhuoyan.flappybird.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.yizhuoyan.flappybird.model.Game;
import com.yizhuoyan.flappybird.model.GameConfig;
import com.yizhuoyan.flappybird.model.scene.GameScene;
import com.yizhuoyan.flappybird.util.ResourceUtil;

public class GameWindow extends JFrame {
	//游戏模型
	private final Game game;
	//双缓冲图片
	private volatile BufferedImage doubleBufferedImg;
	
	private class GamePanel extends JPanel{
		public GamePanel() {
			super(true);
			setLayout(null);
		}
		@Override
		public void doLayout() {
			super.doLayout();
			game.config.canvasWidth=this.getWidth();
			game.config.canvasHeight=this.getHeight();
		}
		@Override
		public void paint(Graphics g) {
			g.drawImage(doubleBufferedImg, 0, 0, null);
		}
		
	}
	
	public GameWindow(Game game) {
		this.game = game;
		this.configWindow();
		this.addEventListener();
		setVisible(true);
	}

	private void configWindow() {
		GameConfig config = game.config;
		setIconImage((Image) ResourceUtil.get(config.gameAppIcon));
		setTitle(config.gameName);
		setSize(config.gameWindowWidth, config.gameWindowHeight);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		JPanel gamePanel=new GamePanel();
		setContentPane(gamePanel);
	}
	

	private void addEventListener() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				GameWindow.this.game.stop();
			}
		});
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				GameWindow gameWindow = GameWindow.this;
				gameWindow.game.currentScene.handleEvent(e);
			}
		});
	}
	
	public void paintScene(final GameScene scene){
		doubleBufferedImg=scene.getView();
		this.getContentPane().repaint();
	}
}

