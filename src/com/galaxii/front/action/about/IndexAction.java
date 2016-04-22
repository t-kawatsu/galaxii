package com.galaxii.front.action.about;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import com.galaxii.common.dto.HotWord;
import com.galaxii.common.entity.CommunityCategory;
import com.galaxii.common.service.CommunityService;
import com.galaxii.front.action.AbstractAction;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int DISP_HOT_WORDS_NUM = 8;
	
	@Resource
	private CommunityService communityService;
	
	private List<HotWord> hotWords;

	@Action(value="about")
	@Override
    public String execute() throws Exception {
		hotWords = communityService.detectHotWords(DISP_HOT_WORDS_NUM);
        return SUCCESS;
    }

	public CommunityCategory[] getCategories() {
		return CommunityCategory.values();
	}
	
	public List<HotWord> getHotWords() {
		return hotWords;
	}
}
