package com.galaxii.front.action.community_event;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityActivityDao;
import com.galaxii.common.dao.CommunityEventDao;
import com.galaxii.common.entity.CommunityContentsCategory;
import com.galaxii.common.entity.CommunityEvent;
import com.galaxii.front.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityEventDao communityEventDao;
	@Resource
	private CommunityActivityDao communityAvtivityDao;
	
	private Integer id;
	private CommunityEvent communityEvent;

	@Action(value="community-event/delete-ajax/{id}",
		results={
			 @Result(name="success", location="community-event/delete-complete.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
		communityEvent = communityEventDao.findById(id);
        if(communityEvent == null 
        	|| !communityEvent.getUserId().equals(getCurrentUser().getId())) {
            return ERROR;
        }
        communityEventDao.delete(communityEvent);
        
        communityAvtivityDao.deleteActivity(CommunityContentsCategory.EVENT, id);
        // user info
        userInformationService.deleteCommunityEvent(
        		communityEvent.getCommunityId(), getCurrentUser());
		return SUCCESS;
    }

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public CommunityEvent getCommunityEvent() {
		return communityEvent;
	}
}