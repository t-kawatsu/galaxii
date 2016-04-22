package com.galaxii.front.action.community_event;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityEventDao;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityEvent;
import com.galaxii.common.service.CommunityEventService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.CommunityEventForm;

public class UpdateAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityEventService communityEventService;
	@Resource
	private CommunityDao communityDao;
	@Resource
	private CommunityEventDao communityEventDao;
	@Resource
	private CommunityEventForm communityEventForm;
	
	private Integer id;
	private Community community;
	private CommunityEvent communityEvent;
	
	@Action(value="community-event/update/{id}",
			results={
			@Result(name="input", location="community-event/update.ftl"),
            @Result(name="success", location="community-event/read/${id}", type="redirect")
		}
	)
    public String createAction() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
		communityEvent = communityEventDao.findById(id);
		if(communityEvent == null
			|| !communityEvent.getUserId().equals(getCurrentUser().getId())) {
			return ERROR;
		}
		community = communityDao.findById(communityEvent.getCommunityId());
        if(!"POST".equals(request.getMethod())) {
        	communityEventService.reflectForm(
        			communityEventForm, communityEvent);
            return INPUT;
        }
        communityEventForm.setTitle(communityEvent.getTitle());
        if(!communityEventForm.validate(this)) {
            return INPUT;
        }
        // update
        communityEvent = communityEventService.update(
        		communityEvent, communityEventForm, getCurrentUser());
		return SUCCESS;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setCommunityEventForm(CommunityEventForm communityEventForm) {
		this.communityEventForm = communityEventForm;
	}
	
	public CommunityEventForm getCommunityEventForm() {
		return communityEventForm;
	}
	
	public Community getCommunity() {
		return community;
	}
	
	public CommunityEvent getCommunityEvent() {
		return communityEvent;
	}
}
