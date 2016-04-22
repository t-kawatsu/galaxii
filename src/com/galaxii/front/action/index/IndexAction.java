package com.galaxii.front.action.index;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dto.CategorizedCommunity;
import com.galaxii.common.dto.HotWord;
import com.galaxii.common.entity.Community;
import com.galaxii.common.service.CommunityService;
import com.galaxii.front.action.AbstractAction;

public class IndexAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int DISP_PICKUPS_NUM = 10;
	private static final int DISP_NEW_NUM = 5;
	private static final int DISP_HOT_WORDS_NUM = 20;
	private static final int DISP_CATEGORIZED_COMMUNITY_NUM = 5;
	private static final int DISP_CATEGORY_NUM = 8;
	
	@Resource
	private CommunityService communityService;

	private List<HotWord> hotWords;
	private List<Community> pickupCommunities;
	private List<Community> newCommunities;
	private List<CategorizedCommunity> categorizedCommunities;
	
	@Action(value="",
		results={ @Result(name="success", location="index/index.ftl") }
	)
	@Override
    public String execute() throws Exception {
		
		// TODO cache key Generation
		
		// pickups
		pickupCommunities = communityService.detectPickups(DISP_PICKUPS_NUM);
		
		// new
		newCommunities = communityService.detectNew(DISP_NEW_NUM);

		// hot words
		hotWords = communityService.detectHotWords(DISP_HOT_WORDS_NUM);
		
		// community categories
		categorizedCommunities = communityService.findCategorizedCommunities(
				DISP_CATEGORY_NUM, DISP_CATEGORIZED_COMMUNITY_NUM);
		
        return SUCCESS;
    }
	
	public List<Community> getPickupCommunities() {
		return pickupCommunities;
	}
	
	public List<Community> getNewCommunities() {
		return newCommunities;
	}

	public List<HotWord> getHotWords() {
		return hotWords;
	}

	public List<CategorizedCommunity> getCategorizedCommunities() {
		return categorizedCommunities;
	}
}
