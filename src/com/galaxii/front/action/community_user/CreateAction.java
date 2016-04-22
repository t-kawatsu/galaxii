package com.galaxii.front.action.community_user;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.User;
import com.galaxii.common.service.CommunityService;
import com.galaxii.front.action.AbstractAction;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private CommunityService communityService;
	@Resource
	private CommunityDao communityDao;
	
	private Integer communityId;
	private boolean isMember;
	private Community community;
	
	@Action(value="community-user/create-ajax/{communityId}",
		results={
            @Result(name="success", location="community-user/create-complete.ftl")
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
        
        // already joined
        if(communityService.isMember(community, user.getId())) {
        	return SUCCESS;
        }
        
        // join
        communityService.joinUser(community, user.getId());
        isMember = true;
        
        // user info
        userInformationService.insertCommunityMember(community, user);
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