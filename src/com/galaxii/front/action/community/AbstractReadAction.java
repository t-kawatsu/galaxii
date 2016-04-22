package com.galaxii.front.action.community;

import java.util.List;

import javax.annotation.Resource;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.dto.RecommendNews;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityActivity;
import com.galaxii.common.entity.CommunityContentsCategory;
import com.galaxii.common.entity.CommunityUser;
import com.galaxii.common.service.CommunityActivityService;
import com.galaxii.common.service.CommunityService;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.action.AbstractAction;

public class AbstractReadAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected int DISP_ACTIVITY_NUM_PAR_PAGE = 15;
	
	protected int DISP_RECOMMEND_NEWS_NUM_PAR_PAGE = 6;
	
	@Resource
	protected CommunityService communityService;
	@Resource
	protected CommunityActivityService communityActivityService;
	@Resource
	protected CommunityDao communityDao;
	@Resource
	protected CommunityUserDao communityUserDao;

	protected Integer id;
	protected Integer contentsId;
	protected Community community;
	protected SimplePager<CommunityUser> communityUsers;
	protected SimplePager<CommunityActivity> communityActivities;
	protected List<RecommendNews> recommendNews;
	protected CommunityContentsCategory communityContentsCategory
		= CommunityContentsCategory.HOME;
	protected boolean isMember;

    public String beforeReadProcess(Integer id) throws Exception {
		// community 
		community = communityService.findById(id);
		if(community == null) {
			return ERROR;
		}
		
    	if(isPjax()) {
    		return SUCCESS;
    	}
		
		// is member ??
		if(getIsLogined()) {
			isMember = communityUserDao.isExistCommunityUser(
				id, getCurrentUser().getId());
		}
		
		// community users
		communityUsers = communityUserDao.paginateByCommunityId(
			id, SUB_CONTENTS_COMMUNITY_USERS_LIMIT, 1);
		
		// recommend informations
		recommendNews = communityService.findRecommendNews(
				community, DISP_RECOMMEND_NEWS_NUM_PAR_PAGE, 1);
		
		return SUCCESS;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setContentsId(Integer contentsId) {
		this.contentsId = contentsId;
	}
	
	public Integer getContentsId() {
		return contentsId;
	}
	
	public Community getCommunity() {
		return community;
	}
	
	public SimplePager<CommunityUser> getCommunityUsers() {
		return communityUsers;
	}
	
	public SimplePager<CommunityActivity> getCommunityActivities() {
		return communityActivities;
	}

	public List<RecommendNews> getRecommendNews() {
		return recommendNews;
	}

	public boolean getIsMember() {
		return isMember;
	}
	
	public void setContents(String contents) {
		communityContentsCategory = CommunityContentsCategory.nameOf(contents);
	}
	
	public CommunityContentsCategory getContents() {
		return communityContentsCategory;
	}

}