package com.yizhuoyan.flappybird.model;

import com.yizhuoyan.flappybird.model.scene.GameOverScene;
import com.yizhuoyan.flappybird.model.scene.GamePlayingScene;
import com.yizhuoyan.flappybird.model.scene.GameReadyScene;
import com.yizhuoyan.flappybird.model.scene.GameScene;
import com.yizhuoyan.flappybird.util.GameAudio;
import com.yizhuoyan.flappybird.util.ResourceUtil;
import com.yizhuoyan.flappybird.view.GameWindow;

public class Game implements Runnable {
	public final static int STATUS_INIT=0,
							STATUS_READY=1,
							STATUS_PLAYING=2,
							STATUS_OVER=9;
	//游戏窗口
	private GameWindow gameWindow;
	//游戏配置
	public final GameConfig config;
	//游戏线程
	private volatile Thread gameThread;
	/**
	 * 游戏状态
	 */
	public int status=STATUS_INIT;

	public GameReadyScene readySence;
	public GamePlayingScene playingSence;
	public GameOverScene overSence;
	public volatile GameScene currentScene;

	public Game(GameConfig cfg) {
		this.config=cfg;
		try{
			this.init();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 游戏初始化
	 */
	public void init()throws Exception{
		//加载资源
		ResourceUtil.loadResource();
		GameAudio.loadAudios();
		//游戏线程
		this.gameThread = new Thread(this);
		//游戏窗口
		this.gameWindow = new GameWindow(this);
		//游戏场景
		this.readySence = new GameReadyScene(this);
		this.playingSence = new GamePlayingScene(this);
		this.overSence = new GameOverScene(this);
		
		this.replay();
	}
	/**
	 * 重新开始-准备
	 */
	public void replay() {
		this.readySence.reset();
		this.playingSence.reset();
		this.overSence.reset();
		this.currentScene = this.readySence;
		this.status=STATUS_INIT;
	}
	
	/**
	 *开始游戏
	 */
	public void beginPlay() {
		this.currentScene = this.playingSence;
		this.status=STATUS_PLAYING;
	}
	
	/**
	 * 游戏结束
	 */
	public void over() {
		GameAudio.play(GameAudio.GAMEOVER);
		this.overSence.reset();
		this.currentScene = this.overSence;
		this.status=STATUS_OVER;
	}
	/**
	 * 退出游戏
	 */
	public void stop() {
		this.gameThread=null;
	}
	/**
	 * 游戏启动
	 */
	public void run() {
		if (Thread.currentThread() != gameThread) {
			this.gameThread.start();
			return;
		}
		
		try {
			long begin = 0;
			long passTime = 0;
			final int fps = this.config.fps;
			final GameWindow win = this.gameWindow;
			while (gameThread!=null) {
				begin = System.currentTimeMillis();
				win.paintScene(this.currentScene);
				passTime = System.currentTimeMillis() - begin;
				if (passTime < fps) {
					Thread.sleep(fps - passTime);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
			

}
