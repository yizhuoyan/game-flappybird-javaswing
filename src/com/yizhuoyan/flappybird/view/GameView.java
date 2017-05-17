package com.yizhuoyan.flappybird.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.Timer;

import com.yizhuoyan.flappybird.model.Game;
import com.yizhuoyan.flappybird.model.GameConfig;
import com.yizhuoyan.flappybird.model.scene.GameScene;

public class GameView extends JComponent implements ActionListener {
	// 双缓冲图片
	private volatile BufferedImage sceneView;
	private final Game game;
	private Timer timer=new Timer(1000,this);
	//游戏fps
	private volatile int frameCount;
	private volatile int lastFrameCount=0;
	
	public GameView(Game g) {
		this.game=g;
		GameConfig cfg=g.config;
		Dimension preferredSize=new Dimension(cfg.canvasWidth,cfg.canvasHeight);
		setPreferredSize(preferredSize);
		timer.start();
	}
	
	@Override
	public void doLayout() {
		super.doLayout();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		lastFrameCount=frameCount;
		frameCount=0;
	}
	@Override
	public void paint(Graphics g) {
		g.drawImage(sceneView, 0, 0, null);
		frameCount++;
		g.drawString("FPS:"+lastFrameCount, 100, 100);
	}

	public void paintScene(final GameScene scene) {
		sceneView = scene.getView();
		repaint();
	}
}
