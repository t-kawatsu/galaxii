package com.galaxii.front.action.community_user;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.User;
import com.galaxii.common.service.CommunityService;
import com.galaxii.front.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityDao communityDao;
	@Resource
	private CommunityService communityService;
	
	private Integer communityId;
	private boolean isMember;
	private Community community;
	
	@Action(value="community-user/delete-ajax/{communityId}",
		results={
            @Result(name="success", location="community-user/delete-complete.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		
		if(!getIsLogined()) {
			return LOGIN;
		}
        
        community = communityDao.findById(communityId);
        if(community == null) {
        	return ERROR;
        }
        
        User user = getCurrentUser();
        
        if(!communityService.isMember(community, user.getId())) {
        	return SUCCESS;
        }
        
        communityService.unJoinUser(community, user.getId());
        
        // user info
        userInformationService.deleteCommunityMember(community, user);
        
		return SUCCESS;
    }
	
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	
	public boolean getIsMember() {
		return isMember;
	}
	
	public Community getCommunity() {
		return community;
	}
}