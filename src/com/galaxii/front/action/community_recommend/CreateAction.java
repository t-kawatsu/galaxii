package com.galaxii.front.action.community_recommend;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.entity.Community;
import com.galaxii.front.action.AbstractAction;

public class CreateAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityUserDao communityUserDao;
	@Resource
	private CommunityDao communityDao;
	
	private Integer communityId;
	private Community community;

	@Action(value="community-recommend/create-ajax/{communityId}",
		results={
			@Result(name="input", location="community-recommend/create.ftl"),
            @Result(name="success", location="community-recommend/create-complete.ftl")
		}
	)
	@Override
	public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
        if(!communityUserDao.isExistCommunityUser(
        		communityId, getCurrentUser().getId())) {
        	return REQUIRE_COMMUNITY_MEMBER;
        }
		community = communityDao.findById(communityId);
		if(community == null) {
			return ERROR;
		}
        return INPUT;
	}
	
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	
	public Integer getCommunityId() {
		return communityId;
	}
}
