package com.galaxii.front.action.help;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import com.galaxii.common.dao.LanguageHelpDao;
import com.galaxii.common.dto.HotWord;
import com.galaxii.common.entity.LanguageHelp;
import com.galaxii.common.service.CommunityService;
import com.galaxii.front.action.AbstractAction;

public class IndexAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int DISP_HOT_WORDS_NUM = 8;

	@Resource
	private LanguageHelpDao languageHelpDao;
	@Resource
	private CommunityService communityService;

	private List<LanguageHelp> languageHelps;
	private List<HotWord> hotWords;
	
	@Action(value="help")
	@Override
    public String execute() throws Exception {
		languageHelps = languageHelpDao.findAll();
		
		// hot words
		hotWords = communityService.detectHotWords(DISP_HOT_WORDS_NUM);
		
		return SUCCESS;
    }
	
	public List<LanguageHelp> getLanguageHelps() {
		return languageHelps;
	}
	
	public List<HotWord> getHotWords() {
		return hotWords;
	}
}