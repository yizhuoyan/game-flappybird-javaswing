package com.yizhuoyan.flappybird.model.spirit;

import java.awt.Graphics2D;

import com.yizhuoyan.flappybird.model.Game;

public abstract class Spirit {
	protected final Game game;
	protected float maxX,maxY;
	protected float w,h;
	protected float x,y;

	public Spirit(Game game) {
		this.game=game;
	}
	abstract void paint(Graphics2D g);
}
