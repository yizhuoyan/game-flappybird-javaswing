package com.yizhuoyan.flappybird.model.spirit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.yizhuoyan.flappybird.model.Game;
import com.yizhuoyan.flappybird.util.GameAudio;

public class GameScore extends Spirit{
	private int score=0;
	private final Font font;
	public GameScore(Game game) {
		super(game);
		font=new Font("Courier", Font.BOLD, 30);
		x=0;
		y=font.getSize()*2;
	}
	public void reset(){
		this.score=0;
		
	}
	public void plus1(){
		this.score++;
		GameAudio.play(GameAudio.GOT_SCORE);
	}
	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Score:"+this.score, x, y);
	}
}
