package com.yizhuoyan.flappybird.model;

public class GameConfig {
	/**
	 * 游戏窗口大小
	 */
	final public int gameWindowWidth=800,gameWindowHeight=600;
	/**
	 * 游戏画布大小,启动时计算得出
	 */
	public int canvasWidth,canvasHeight;
	
	public int fps=1000/60;
	
	public float birdSpeed=2f;
	
	public String gameName="FlappyBird";
	public String gameAppIcon="ico-app.png";
}
