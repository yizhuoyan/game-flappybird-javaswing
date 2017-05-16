package com.yizhuoyan.flappybird;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.yizhuoyan.flappybird.model.Game;
import com.yizhuoyan.flappybird.model.GameConfig;

public class App {
	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					GameConfig cfg=new GameConfig();
					Game game=new Game(cfg);
					game.run();		
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
