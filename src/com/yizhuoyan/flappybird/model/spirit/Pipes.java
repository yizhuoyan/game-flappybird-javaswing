package com.yizhuoyan.flappybird.model.spirit;

import java.awt.Graphics2D;

import com.yizhuoyan.flappybird.model.Game;

public class Pipes extends Spirit {
	
	public Pipe first;
	private Pipe last;
	private int pipeIntervalX=150;
	public void setMaxXY(int mx,int my){
		this.maxX=mx;
		this.maxY=my;
	}
	public Pipes(Game game) {
		super(game);
		this.reset();
	}
	public void reset(){
		last=first=new Pipe(game,maxX,maxY);
	}
	@Override
	public void paint(Graphics2D g) {
		
		Pipe first=this.first;
		if(first.x+first.w<=0){
			Pipe newLast=this.first;
			this.first=this.first.next;
			newLast.destroy();
		}
		Pipe last=this.last;
		if(maxX-(last.x+last.w)>=pipeIntervalX){
			Pipe newLast=new Pipe(game,maxX,maxY);
			this.last.next=newLast;
			this.last=newLast;
		}
		Pipe p=this.first;
		do{
			p.paint(g);
			p=p.next;
		}while(p!=null);
	}
	
}
