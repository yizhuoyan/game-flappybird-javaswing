package com.yizhuoyan.flappybird.util;

import java.util.Random;

public class Util {
	private static final Random random=new Random();
	
	public static int randomInt(int a,int b){
		return (int)(Math.random()*(b-a))+a;
	} 
}
