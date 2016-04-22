package com.galaxii.common.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class EmotionUtil {
	
	private static String EMOTION_DIR = 
			"/usr/local/tomcat/webapps/ROOT/assets/common/images/emotions";
	private static String[] EMOTION_FILE_EXTENTIONS = 
		{"png", "gif"};
	
	public static final List<String> EMOTIONS = 
			fetchConcreteEmotionNames();
	public static final String[] EMOTION_MARKS = 
			getEmotionMarks();
	
	public static List<String> fetchConcreteEmotionNames() {
		File emoDir = new File(EMOTION_DIR);
		File[] emos = emoDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				for(String row : EMOTION_FILE_EXTENTIONS) {
					if(name.matches(".*\\." +row+ "$")) {
						return true;
					}
				}
				return false;
			}
			
		});
		if(emos == null) {
			return null;
		}
		List<String> emoNames = new ArrayList<String>();
		for(File row : emos) {
			// 拡張子をのぞいた部分
			int point = row.getName().lastIndexOf(".");
			if (point != -1) {
				emoNames.add( row.getName().substring(0, point) );
			}
		}
		return emoNames;
	}

	private static String[] getEmotionMarks() {
    	if(EmotionUtil.EMOTIONS == null) {
    		return null;
    	}
    	List<String> ret = new ArrayList<String>();
    	for(String emo : EmotionUtil.EMOTIONS) {
    		ret.add("[["+emo+"]]");
    	}
    	return ret.toArray(new String[]{});
	}
}
