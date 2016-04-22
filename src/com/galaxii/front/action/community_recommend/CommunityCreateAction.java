package com.galaxii.front.action.community_recommend;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.entity.CommunityRecommend;
import com.galaxii.common.entity.CommunityUser;
import com.galaxii.common.entity.User;
import com.galaxii.common.service.CommunityActivityService;
import com.galaxii.common.service.CommunityRecommendService;
import com.galaxii.common.service.CommunityService;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.action.AbstractAction;

public class CommunityCreateAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int USER_CUMMUNITY_DISP_NUM_PAR_PAGE = 15;

	@Resource
	private CommunityService communityService;
	@Resource
	private CommunityUserDao communityUserDao;
	@Resource
	private CommunityRecommendService communityRecommendService;
	@Resource
	private CommunityActivityService communityActivityService;
	
	private SimplePager<CommunityUser> communityUsers;
	private Integer page;
	private Integer communityId;
	private Integer recommendCommunityId;
	
	@Action(value="community-recommend/search-user-community-ajax/{communityId}",
		results={
            @Result(name="success", location="community-recommend/_search-user-community.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
        User user = getCurrentUser();
        communityUsers = communityService.paginateUserCommunities(
        		user.getId(), USER_CUMMUNITY_DISP_NUM_PAR_PAGE, page);
		return SUCCESS;
    }
	
	@Action(value="community-recommend/community-create-ajax/{communityId}/{recommendCommunityId}",
			results={
	            @Result(name="success", location="community-recommend/create-complete.ftl")
			}
		)
	public String createAjax() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
	    User user = getCurrentUser();
        if(!communityUserDao.isExistCommunityUser(
        		communityId, user.getId())) {
        	return ERROR;
        }
        if(!communityUserDao.isExistCommunityUser(
        		recommendCommunityId, user.getId())) {
        	return ERROR;
        }
        
        CommunityRecommend communityRecommend = communityRecommendService.create(
        		communityId, recommendCommunityId, getCurrentUser().getId());
        
        // activity
        communityActivityService
        	.insertCommunityRecommendActivity(communityRecommend);
        // user info
        userInformationService.insertCommunityRecommend(communityId, user);
		return SUCCESS;
	}
	
	public SimplePager<CommunityUser> getCommunityUsers() {
		return communityUsers;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}
	
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	
	public Integer getCommunityId() {
		return communityId;
	}
	
	public void setRecommendCommunityId(Integer recommendCommunityId) {
		this.recommendCommunityId = recommendCommunityId;
	}
}