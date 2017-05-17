package com.yizhuoyan.flappybird;

import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.yizhuoyan.flappybird.model.Game;
import com.yizhuoyan.flappybird.model.GameConfig;
import com.yizhuoyan.flappybird.util.ResourceUtil;
import com.yizhuoyan.flappybird.view.GameView;

public class App {
	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					gameRun();	
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private static void gameRun(){
		GameConfig cfg=new GameConfig();
		final Game game=new Game(cfg);
		JFrame w=new JFrame();
		w.setContentPane(game.getView());
		w.pack();
		w.setIconImage((Image) ResourceUtil.get(cfg.gameAppIcon));
		w.setTitle(cfg.gameName);
		w.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		w.setResizable(false);
		w.setLocationRelativeTo(null);
		w.setVisible(true);
		w.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				game.stop();
			}
		});
		w.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				game.currentScene.handleEvent(e);
			}
		});
		game.run();
	}
}
