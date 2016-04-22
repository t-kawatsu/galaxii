package com.galaxii.front.action.community_event;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityEvent;
import com.galaxii.common.service.CommunityActivityService;
import com.galaxii.common.service.CommunityEventService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.CommunityEventForm;

public class CreateAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityActivityService communityActivityService;
	@Resource
	private CommunityEventService communityEventService;
	@Resource
	private CommunityUserDao communityUserDao;
	@Resource
	private CommunityDao communityDao;
	@Resource
	private CommunityEventForm communityEventForm;
	
	private Integer id;
	private Integer communityId;
	private Date date;
	private Community community;

	@Action(value="community-event/create/{communityId}",
			results={
			@Result(name="input", location="community-event/create.ftl"),
            @Result(name="success", location="community-event/read/${id}", type="redirect")
		}
	)
    public String createAction() throws Exception {
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
		if(date == null) {
			date = new Date();
		}
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }
        if(!communityEventForm.validate(this)) {
            return INPUT;
        }
        // create
        CommunityEvent communityEvent = communityEventService.create(
        		communityId, communityEventForm, getCurrentUser());
        id = communityEvent.getId();
        // activity
        communityActivityService.insertCommunityEventActivity(communityEvent);
        // user info
        userInformationService.insertCommunityEvent(
        	communityId, getCurrentUser());
		return SUCCESS;
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
	
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	
	public void setDate(String date) throws Exception {
		this.date = DateUtils.parseDate(date, "yyyy-MM-dd");
	}
	
	public Date getDate() {
		return date;
	}
	
	public Community getCommunity() {
		return community;
	}
}
