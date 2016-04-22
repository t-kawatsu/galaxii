package com.galaxii.front.action.community_event_user;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityEventDao;
import com.galaxii.common.dao.CommunityEventUserDao;
import com.galaxii.common.entity.CommunityEvent;
import com.galaxii.common.entity.CommunityEventUser;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.action.AbstractAction;

public class IndexAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int DISP_EVENT_JOINED_USERS = 20;

	@Resource
	private CommunityEventDao communityEventDao;
	@Resource
	private CommunityEventUserDao communityEventUserDao;
	
	private Integer id;
	private Integer page;
	private CommunityEvent communityEvent;
	private SimplePager<CommunityEventUser> communityEventUsers;
	
	@Action(value="community-event-user/index/{id}",
		results={
            @Result(name="success", location="community-event-user/index.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		// event
		communityEvent = communityEventDao.findById(id);
		if(communityEvent == null) {
			return ERROR;
		}
		// joined users
		communityEventUsers = communityEventUserDao
			.paginateByCommunityEventId(id, DISP_EVENT_JOINED_USERS, page);

		return SUCCESS;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public CommunityEvent getCommunityEvent() {
		return communityEvent;
	}
	
	public SimplePager<CommunityEventUser> getCommunityEventUsers() {
		return communityEventUsers;
	}

}