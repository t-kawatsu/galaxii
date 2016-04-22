package com.galaxii.front.action.community_event_user;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityEventDao;
import com.galaxii.common.dao.CommunityEventUserDao;
import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.entity.CommunityEvent;
import com.galaxii.common.entity.User;
import com.galaxii.common.service.CommunityEventService;
import com.galaxii.front.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityUserDao communityUserDao;
	@Resource
	private CommunityEventDao communityEventDao;
	@Resource
	private CommunityEventUserDao communityEventUserDao;
	@Resource
	private CommunityEventService communityEventService;
	
	private Integer communityEventId;
	private boolean isUserJoined;
	private CommunityEvent communityEvent;
	
	@Action(value="community-event-user/delete-ajax/{communityEventId}",
		results={
            @Result(name="success", location="community-event-user/delete-complete.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		
		if(!getIsLogined()) {
			return LOGIN;
		}
        
        communityEvent = communityEventDao.findById(communityEventId);
        if(communityEvent == null) {
        	return ERROR;
        }
        
        User user = getCurrentUser();
        
        if(!communityUserDao.isExistCommunityUser(
        		communityEvent.getCommunityId(), user.getId())) {
        	return ERROR;
        }
        
        communityEventService.unJoinUser(communityEvent, user.getId());
        
		return SUCCESS;
    }
	
	public void setCommunityEventId(Integer communityEventId) {
		this.communityEventId = communityEventId;
	}
	
	public boolean getIsUserJoined() {
		return isUserJoined;
	}
	
	public CommunityEvent getCommunityEvent() {
		return communityEvent;
	}
}