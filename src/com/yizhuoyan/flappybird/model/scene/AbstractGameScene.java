package com.yizhuoyan.flappybird.model.scene;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.yizhuoyan.flappybird.model.Game;

public abstract class AbstractGameScene implements GameScene {
	protected final BufferedImage canvas;
	protected final Game game;
	protected final int w,h;
	private final Graphics2D g;
	
	public AbstractGameScene(Game game) {
		this.game=game;
		this.w=game.config.canvasWidth;
		this.h=game.config.canvasHeight;
		canvas=new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
		g=canvas.createGraphics();
	}
	
	public final BufferedImage getView(){
		
		this.paint(g);
		return this.canvas;
	}
	@Override
	public void reset() {
		
	}
	
	protected abstract void paint(Graphics2D g);
}
