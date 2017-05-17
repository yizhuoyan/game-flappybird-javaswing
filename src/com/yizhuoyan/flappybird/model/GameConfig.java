package com.yizhuoyan.flappybird.model;

public class GameConfig {
	/**
	 * 游戏画面大小
	 */
	public int canvasWidth=800,canvasHeight=600;
	
	public int fps=Math.round(1000/60f);
	
	public float birdSpeed=2f;
	
	public String gameName="FlappyBird";
	public String gameAppIcon="ico-app.png";
}
