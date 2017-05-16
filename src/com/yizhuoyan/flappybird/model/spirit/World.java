package com.yizhuoyan.flappybird.model.spirit;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.yizhuoyan.flappybird.model.Game;
import com.yizhuoyan.flappybird.model.GameConfig;
import com.yizhuoyan.flappybird.util.ResourceUtil;

public class World extends Spirit {
	private static final BufferedImage TILE_SKY=ResourceUtil.get("bg-sky.png"),
										TILE_GROUND=ResourceUtil.get("bg-ground.png");
	private final Color groundColor=new Color(0xDED895),
						 skyColor=new Color(0x008793);
	
	private Rectangle sky,ground;
	private float skyCurrentX=0;
	private float groundCurrentX=0;
	
	private final int skyTileW,skyTileH,skyDrawX,skyDrawY;
	private final int groundTileW,groundTileH;
	
	private final float  worldSpeed;
	

	
	
	
	
	public World(Game game ) {
		super(game);
		GameConfig config=game.config;
		this.maxX=config.canvasWidth;
		this.maxY=config.canvasHeight;
		{
		int skyHeight=Math.round(this.maxY*0.8f);
		
		this.sky=new Rectangle(0, 0, (int)maxX, skyHeight);
		this.ground=new Rectangle(0, skyHeight, (int)maxX,(int)(maxY-skyHeight));
		}
		{
			this.skyTileW=TILE_SKY.getWidth();
			this.skyTileH=TILE_SKY.getHeight();
			this.skyDrawX=0;
			this.skyDrawY=sky.height-skyTileH;
			this.groundTileW=TILE_GROUND.getWidth();
			this.groundTileH=TILE_GROUND.getHeight();
		}
		this.worldSpeed=config.birdSpeed*0.5f;
		
	}
	public int getSkyHeight(){
		return this.sky.height;
	}
	public int getSkyWidth(){
		return this.sky.width;
	}
	public void reset(){
		skyCurrentX=0;
		groundCurrentX=0;
	}
	@Override
	public void paint(Graphics2D g) {
		final int skyW=sky.width,groundW=ground.width;
		final int skyH=sky.height,groundH=ground.height;
		
		if(game.status==Game.STATUS_PLAYING){
			final float speed=this.worldSpeed;
			skyCurrentX-=speed;
			if(skyCurrentX+skyTileW<0){
				skyCurrentX=0;
			}
			groundCurrentX-=speed;
			if(groundCurrentX+groundTileW<0){
				groundCurrentX=0;
			}
		}
		//sky
		g.setColor(skyColor);
		g.fillRect(0, 0, skyW, skyDrawY);
		for(int i=Math.round(skyCurrentX);i<skyW;i+=skyTileW){
			g.drawImage(TILE_SKY,i,skyDrawY,null);
		}
		
		Rectangle ground=this.ground;
		//ground
		g.setColor(groundColor);
		g.fillRect(ground.x, ground.y, groundW,groundH);
		for (int i = Math.round(groundCurrentX);i<groundW; i+=groundTileW) {
			g.drawImage(TILE_GROUND,i,ground.y,null);	
		}
		
		
	}
	
}
