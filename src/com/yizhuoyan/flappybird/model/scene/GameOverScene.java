package com.yizhuoyan.flappybird.model.scene;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.EventObject;

import com.yizhuoyan.flappybird.model.Game;
import com.yizhuoyan.flappybird.util.GameAudio;
import com.yizhuoyan.flappybird.util.ResourceUtil;

public class GameOverScene extends AbstractGameScene{
	private static final BufferedImage OVER_IMAGE=ResourceUtil.get("bg-over.png");
	private float x,y;
	private float upSpeed;
	private int maxY;
	private GamePlayingScene playingScene;
	public GameOverScene(Game game) {
		super(game);
		upSpeed=1f;
		this.playingScene=game.playingSence;
	}
	public void reset(){
		int winX=game.config.canvasWidth;
		int winY=game.config.canvasHeight;
		this.x=(winX-OVER_IMAGE.getWidth())/2;
		this.y=winY;
		this.maxY=(winY-OVER_IMAGE.getHeight())/2;
	}
	@Override
	public void paint(Graphics2D g) {
		playingScene.paint(g);
		int drawY=Math.round(this.y);
		int drawX=Math.round(this.x);
 		g.drawImage(OVER_IMAGE, drawX, drawY, null);
 		if(y<=maxY){
 			y=maxY;
 		}else{
 			y-=upSpeed;	
 		}
	}
	@Override
	public void handleEvent(EventObject evt) {
		if(y==maxY){
			game.replay();
		}
	}
}
