package com.yizhuoyan.flappybird.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.swing.ImageIcon;

public class ResourceUtil {
	private static final String RESOURCE_DIRECTORY_PATH="/asserts";
	
	private static final Map<String,Object> RESOURCE_MAP=new HashMap<String,Object>(20);
	private static boolean UNLOAD=true;
	public static  void loadResource()throws Exception{
		if(UNLOAD){
			URL url=ResourceUtil.class.getResource(RESOURCE_DIRECTORY_PATH);
			File dir=new File(url.toURI());
			loadResourceFromDir(dir);
			UNLOAD=false;
		}
	}
	private static void loadResourceFromDir(File dir)throws Exception{
		if(dir.isDirectory()){
			File[] files=dir.listFiles();
			if(files==null)throw new IllegalStateException("�޷���ȡ��Ϸ��Դ,����·��"+dir.getCanonicalPath());
			for (File f : files) {
				if(f.isDirectory()){
					loadResourceFromDir(f);
				}else{
					Object resource=loadResourceFromFile(f);
					RESOURCE_MAP.put(f.getName(), resource);
				}
			}
		}
	}
	private static Object loadResourceFromFile(File file)throws Exception{
		String fileName=file.getName();
		if(fileName.matches("[^.]+\\.png")){
			Image image=ImageIO.read(file);
			return image;
		}else{
			Object audio=AudioSystem.getAudioInputStream(file);
			return audio;
		}
		
	}
	public static <T> T get(String resouceName){
		return (T)RESOURCE_MAP.get(resouceName);
	}
}
