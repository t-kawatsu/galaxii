package com.galaxii.front.action.community_user;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.dto.RecommendNews;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityActivity;
import com.galaxii.common.entity.CommunityUser;
import com.galaxii.common.service.CommunityActivityService;
import com.galaxii.common.service.CommunityService;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.action.AbstractAction;

public class IndexAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int DISP_USERS_NUM_PAR_PAGE = 20;
	private int DISP_ACTIVITY_NUM_PAR_PAGE = 15;

	@Resource
	private CommunityService communityService;
	@Resource
	private CommunityDao communityDao;
	@Resource
	private CommunityUserDao communityUserDao;
	@Resource
	private CommunityActivityService communityActivityService;
	
	private Integer id;
	private Integer page;
	private Community community;
	private SimplePager<CommunityUser> communityUsers;
	private SimplePager<CommunityActivity> communityActivities;
	private List<RecommendNews> recommendNews;
	
	@Action(value="community-user/index/{id}",
		results={
            @Result(name="success", location="community-user/index.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		// community 
		community = communityService.findById(id);
		if(community == null) {
			return ERROR;
		}
		
		// community users
		communityUsers = communityUserDao.paginateByCommunityId(
			id, DISP_USERS_NUM_PAR_PAGE, page);
		
		// activates
		communityActivities = communityActivityService.factorySessionPaginator(
				sessUtil, id, DISP_ACTIVITY_NUM_PAR_PAGE).clearPage().paginate();
		
		// recommend informations
		recommendNews = communityService.findRecommendNews(community, 6, 1);
		
		return SUCCESS;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Community getCommunity() {
		return community;
	}
	
	public SimplePager<CommunityUser> getCommunityUsers() {
		return communityUsers;
	}
	
	public List<RecommendNews> getRecommendNews() {
		return recommendNews;
	}
	
	public SimplePager<CommunityActivity> getCommunityActivities() {
		return communityActivities;
	}
}