package com.yizhuoyan.flappybird.model.spirit;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.yizhuoyan.flappybird.model.Game;
import com.yizhuoyan.flappybird.util.ResourceUtil;
import com.yizhuoyan.flappybird.util.Util;

public class Pipe extends Spirit{
	
	private static final BufferedImage[] PIPE_IMAGES=getImages();
	private static BufferedImage[] getImages(){
		BufferedImage[] images=new BufferedImage[4];
		for (int i = 0; i < images.length; i++) {
			images[i]=ResourceUtil.get("sp-pipe"+i+".png");;
		}
		return images;
		
	}
	public float x,uy,dy;
	
	private int uDrawY1,uDrawY2,dDrawY1,dDrawY2;
	private  BufferedImage 	downPipeImage,upPipeImage,downPipeimage1px,upPipeImage1px;
	private final float birdSpeed;
	public Pipe next;
	
	public Pipe(Game game,float maxX,float maxY) {
		super(game);
		this.createImages(PIPE_IMAGES[Util.randomInt(0, PIPE_IMAGES.length)]);
		this.maxX=maxX;
		this.maxY=maxY;
		this.birdSpeed=game.config.birdSpeed;
		x=this.maxX;
		randomPosition();
	}
	private void createImages(BufferedImage image){
		w=image.getWidth();
		int ph=image.getHeight()/2;
		upPipeImage=image.getSubimage(0, 0, (int)w, ph);
		upPipeImage1px=upPipeImage.getSubimage(0, 0, (int)w, 1);
		downPipeImage=image.getSubimage(0, ph, (int)w, ph);
		downPipeimage1px=downPipeImage.getSubimage(0, ph-1, (int)w, 1);
	}
	@Override
	public void paint(Graphics2D g) {
		final BufferedImage upPipe=upPipeImage,
				upPipe1px=upPipeImage1px,
				downPipe=downPipeImage,
				downPipe1px=downPipeimage1px;
		int drawX=Math.round(x);
		for (int i = uDrawY1; i>0; i--) {
			g.drawImage(upPipe1px,drawX,i,null);
		}
		g.drawImage(upPipe,drawX, uDrawY1,null);
		g.drawImage(downPipe,drawX, dDrawY1,null);
		
		for (int i = dDrawY2; i <maxY; i++) {
			g.drawImage(downPipe1px,drawX, i,null);
		}
		if(game.status==Game.STATUS_PLAYING){
			x-=birdSpeed;
		}
	}
	public void randomPosition(){
		uDrawY2=Util.randomInt(50, (int)(maxY/2));
		uDrawY1=uDrawY2-upPipeImage.getHeight();
		dDrawY1=uDrawY2+150;
		dDrawY2=dDrawY1+downPipeImage.getHeight();
		
		uy=uDrawY2;
		dy=dDrawY1;
	}
	public boolean inPipeSpace(float y1,float y2){
		return uy<y1&&y2<dy;
	}
	public void destroy(){
		this.next=null;
		this.downPipeImage=null;
		this.downPipeimage1px=null;
		this.upPipeImage=null;
		this.upPipeImage1px=null;
	}
	
}
