package com.yizhuoyan.flappybird.model.spirit;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.yizhuoyan.flappybird.model.Game;
import com.yizhuoyan.flappybird.model.GameConfig;
import com.yizhuoyan.flappybird.util.GameAudio;
import com.yizhuoyan.flappybird.util.ResourceUtil;

public class Bird extends Spirit {

	private BufferedImage birdImage = ResourceUtil.get("sp-birds.png");
	private BufferedImage[] birdFrames = new BufferedImage[3];
	// 每帧移动距离
	private float frameY = 0;
	// 每帧下落距离
	private float fallY = 0f;

	// 下降速度
	private final float fallSpeed = .8f;
	// 每帧飞扬距离
	private final float flappyY = fallSpeed * 12;
	private final float flySpeed;
	// 鸟角度
	private double angle;
	// 鸟死的角度
	private final double dieAngle = Math.PI / 2;
	// 当前鸟帧下标
	private int currentFrameIndex = 0;
	// 最近改变帧下标时间
	private long changeFrameLastTime = 0;
	// 鸟挥动翅膀的速度
	private final long changeFrameIntervalTime = 120;
	private BirdStatus status;
	// 鸟前面的管子
	private Pipe frontPipe;

	public Bird(Game game,int maxX,int maxY) {
		super(game);
		GameConfig config = game.config;
		this.maxY =maxX;
		this.maxX = maxY;
		this.flySpeed = config.birdSpeed;
		this.w = birdImage.getWidth() / 3;
		this.h = birdImage.getHeight();
		for (int i = 0; i < birdFrames.length; i++) {
			birdFrames[i] = birdImage.getSubimage((int) (i * w), 0, (int) w, (int) h);
		}
		status = BirdStatus.READY;
	}

	/**
	 * 数据重置
	 */
	public void reset() {
		this.x = (this.maxX - this.w) / 2;
		this.y = 0;// (this.maxY-this.h)/2;
		status = BirdStatus.READY;
		fallY = 0;
		frameY = 0;
		this.angle=0;
	}
	public void fly(){
		this.status=BirdStatus.FLYING;
	}
	/**
	 * 返回鸟是否正常飞行
	 * 
	 * @return
	 */
	public boolean isFlying() {
		return this.status == BirdStatus.FLYING;
	}

	/**
	 * 鸟撞到管子
	 */
	private void hitPipe() {
		this.status = BirdStatus.DIZZY;
		GameAudio.play(GameAudio.HIT);
		GameAudio.play(GameAudio.DIZZY);
		game.over();
	}

	/**
	 * 鸟死掉
	 */
	public void die() {
		this.status = BirdStatus.DIE;
		GameAudio.play(GameAudio.DIE);
		if(game.status!=Game.STATUS_OVER){
			game.over();
		}
	}

	/**
	 * 设置鸟最大飞行高度
	 * 
	 * @param y
	 */
	public void setBirdMaxY(int y) {
		this.maxY = y;
	}
	/**
	 * 计算是否撞击管子
	 */
	private boolean isHitAPipe() {
		Pipe p = this.frontPipe;
		if (this.x + this.w >= p.x) {
			if (!p.inPipeSpace(this.y, this.y + this.h)) {
				return true;
			}
		}
		return false;
	}

	
	private boolean testHitPipe() {
		Pipe p = this.frontPipe;
		if (p != null) {
			if (isHitAPipe()) {
				this.hitPipe();
				return true;
			} else if (this.x > p.x + p.w) {
				// 穿过一个管子
				this.frontPipe = this.frontPipe.next;
				game.playingSence.birdPassAPipe();
			}
		}
		return false;
	}

	/**
	 * 计算撞击到地上
	 */
	private void testHitGround() {
		if (y + h >= maxY) {
			y = maxY - this.h + 5;
			angle = dieAngle;
			this.die();
		}

	}

	/**
	 * 计算撞晕后下落过程中情况
	 */
	private void figureBuzzyFall() {
		// 落到地面
		if (y + h >= maxY) {
			y = maxY - this.h + 5;
			angle = dieAngle;
			this.die();
		}
	}

	private void figureFlappy() {
		// 如果已到顶,flappy结束
		if (frameY - flappyY >= 0) {
			this.status = BirdStatus.FLYING;
			fallY = 0;// 重新计算下落距离
			frameY = 0;
		} else {
			frameY -= flappyY;// flappy
		}
	}

	private void figureFall() {
		fallY += fallSpeed;
		frameY = fallY;
	}

	/**
	 * 计算旋转角度
	 */
	private void figureAngle() {
		angle = Math.atan(frameY * 0.1);
	}

	public void setFrontPipe(Pipe pipe) {
		this.frontPipe = pipe;
	}

	private void paintFlyAnimation() {
		if (System.currentTimeMillis() - changeFrameLastTime > changeFrameIntervalTime) {
			currentFrameIndex++;
			if (currentFrameIndex >= birdFrames.length) {
				currentFrameIndex = 0;
			}
			changeFrameLastTime = System.currentTimeMillis();
		}
	}

	public void flappy() {
		if (this.status == BirdStatus.FLYING) {
			this.status = BirdStatus.FLAPPY;
			fallY = 0;
			GameAudio.play(GameAudio.FLAPPY);
		}
	}
	//TODO:ready
	private void paintReady(){
		this.x=(maxX-this.w)/2;
		this.y=(maxY-this.h)/2;
	}
	@Override
	public void paint(Graphics2D g) {
		
		switch (status) {
		case READY:
			paintFlyAnimation();
			paintReady();
			break;
		case FLAPPY:
			paintFlyAnimation();
			// fall
			figureFall();
			// flappy
			figureFlappy();
			// angle
			figureAngle();
			this.y += frameY;
			if (!testHitPipe()) {
				testHitGround();
			}
			break;
		case FLYING:
			paintFlyAnimation();
			// fall
			figureFall();
			// angle
			figureAngle();
			this.y += frameY;
			if (!testHitPipe()) {
				testHitGround();
			}
			break;
		case DIZZY:
			figureFall();
			// angle
			figureAngle();
			this.y += frameY;
			figureBuzzyFall();
			break;
		case DIE:
			g.setClip((int)x, (int)y, (int)w, (int)(h-5));
			break;
		}
		float cx = x + w / 2;
		float cy = y + h / 2;
		g.rotate(angle, cx, cy);
		g.drawImage(birdFrames[currentFrameIndex], Math.round(x), Math.round(y), null);
		g.rotate(-angle, cx, cy);
		g.setClip(null);
	}
}
