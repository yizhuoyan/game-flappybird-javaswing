package com.yizhuoyan.flappybird.model.scene;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.EventObject;

import com.yizhuoyan.flappybird.model.Game;
import com.yizhuoyan.flappybird.model.GameConfig;
import com.yizhuoyan.flappybird.model.spirit.Bird;
import com.yizhuoyan.flappybird.model.spirit.World;
import com.yizhuoyan.flappybird.util.ResourceUtil;

public class GameReadyScene extends AbstractGameScene{
	private BufferedImage gameReadyImage=ResourceUtil.get("bg-ready.png");
	private int gameReadyImageX,gameReadyImageY;
	public World world;
	public Bird bird;
	public GameReadyScene(Game game) {
		super(game);
		this.bird=new Bird(game);
		GameConfig config=game.config;
		world=new World(game);
		this.gameReadyImageX=(config.canvasWidth-gameReadyImage.getWidth())/2;
		this.gameReadyImageY=(config.canvasHeight-gameReadyImage.getHeight())/2;
	}
	@Override
	public void reset() {
		world.reset();
	}
	@Override
	public void paint(Graphics2D g) {
		world.paint(g);
		g.drawImage(gameReadyImage, gameReadyImageX, gameReadyImageY, null);
		bird.paint(g);
	}
	
	@Override
	public void handleEvent(EventObject evt) {
		bird.fly();
		game.beginPlay();
	}

}
