package com.yizhuoyan.flappybird.model.scene;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.EventObject;

import com.yizhuoyan.flappybird.model.Game;
import com.yizhuoyan.flappybird.model.spirit.Bird;
import com.yizhuoyan.flappybird.model.spirit.GameScore;
import com.yizhuoyan.flappybird.model.spirit.Pipes;
import com.yizhuoyan.flappybird.model.spirit.World;

public class GamePlayingScene extends AbstractGameScene {
	public final World world;
	public final Bird bird;
	public final Pipes pipe;
	public final GameScore score;
	public GamePlayingScene(Game game) {
		super(game);
		this.world=game.readySence.world;
		this.bird=game.readySence.bird;
		int skyHeight=this.world.getSkyHeight();
		int skyWidth=this.world.getSkyWidth();
		this.bird.setBirdMaxY(skyHeight);
		this.pipe=new Pipes(game);
		this.bird.setFrontPipe(pipe.first);
		this.pipe.setMaxXY(skyWidth, skyHeight);
		this.score=new GameScore(game);
	}
	
	public void reset(){
		this.bird.reset();
		this.pipe.reset();
		this.bird.setFrontPipe(pipe.first);
		this.score.reset();
	}
	@Override
	public void paint(Graphics2D g) {
		world.paint(g);
		pipe.paint(g);
		score.paint(g);
		bird.paint(g);
	}
	@Override
	public void handleEvent(EventObject evt) {
		bird.flappy();
	}
	public void birdPassAPipe(){
		this.score.plus1();
	}
	
	
}
