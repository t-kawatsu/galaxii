package com.galaxii.common.views.freemarker.template;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import com.galaxii.common.util.EmotionUtil;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class EmotionMethod implements TemplateMethodModel {
	
	private final static String[] EMOTION_REPLACES = getEmotionReplaces();
	
    public TemplateModel exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		if(args.size() < 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		String text = (String)args.get(0);
		if(text == null || EmotionUtil.EMOTION_MARKS == null) {
			return new SimpleScalar(null);
		}
		return new SimpleScalar( StringUtils.replaceEach(
				text, EmotionUtil.EMOTION_MARKS, EMOTION_REPLACES) );
    }
	
    private static String[] getEmotionReplaces() {
    	if(EmotionUtil.EMOTIONS == null) {
    		return null;
    	}
    	List<String> ret = new ArrayList<String>();
    	for(String emo : EmotionUtil.EMOTIONS) {
    		ret.add("<span class='emotion sprite-emo-" + emo + "'></span>");
    	}
    	return ret.toArray(new String[]{});
    }
}
