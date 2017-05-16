package com.yizhuoyan.flappybird.model.scene;

import java.awt.image.BufferedImage;
import java.util.EventObject;

public interface GameScene {
	
	void handleEvent(EventObject evt);
	BufferedImage getView(); 
	void reset();
}
