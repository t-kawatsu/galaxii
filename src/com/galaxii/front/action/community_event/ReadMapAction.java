package com.galaxii.front.action.community_event;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityEventDao;
import com.galaxii.common.entity.CommunityEvent;
import com.galaxii.front.action.AbstractAction;

public class ReadMapAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private CommunityEventDao communityEventDao;

	private Integer id;
	private CommunityEvent communityEvent;

	@Action(value="community-event/read-map-ajax/{id}",
		results={
			@Result(name="success", location="community-event/read-map.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		communityEvent = communityEventDao.findById(id);
		if(communityEvent == null || communityEvent.getLat() == null) {
			return ERROR;
		}
		return SUCCESS;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}

	public CommunityEvent getCommunityEvent() {
		return communityEvent;
	}
}