package com.galaxii.front.action.common;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;

import com.galaxii.common.util.EmotionUtil;
import com.galaxii.front.action.AbstractAction;

public class EmotionAction extends AbstractAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value="common/emotion-ajax")
    public String execute() throws Exception {
        return SUCCESS;
    }
	
	public List<String> getEmotions() {
		return EmotionUtil.EMOTIONS;
	}

}

