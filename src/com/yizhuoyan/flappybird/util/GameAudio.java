package com.yizhuoyan.flappybird.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class GameAudio {
	public static  Clip FLAPPY,GAMEOVER,HIT,DIZZY,DIE,GOT_SCORE;
	private static final  ExecutorService EXECUTOR_SERVICE=Executors.newCachedThreadPool();
	
	public static void loadAudios()throws Exception{
		FLAPPY=getClip("flappy.wav");
		GAMEOVER=getClip("gameover.wav");
		DIZZY=getClip("dizzy.wav");
		DIE=getClip("die.wav");
		HIT=getClip("hit.wav");
		GOT_SCORE=getClip("gotscore.wav");
	}
	private static Clip getClip(String path)throws Exception{
			AudioInputStream in=ResourceUtil.get(path);
			AudioFormat audioFormat=in.getFormat();
			DataLine.Info dataLine=new DataLine.Info(Clip.class, audioFormat); 
			Clip clip=(Clip)AudioSystem.getLine(dataLine);
			clip.open(in);
			return clip;
	}
	public static void play(final Clip clip){
				clip.setFramePosition(0);
				clip.start();
	}
	public static void play(final Clip clip1,final Clip clip2){
		clip1.setFramePosition(0);
		clip1.addLineListener(new LineListener() {
			@Override
			public void update(LineEvent event) {
				if(event.getType()==LineEvent.Type.STOP){
					play(clip2);
					clip1.removeLineListener(this);
				}
			}
		});
		clip1.start();
	}
	
}
