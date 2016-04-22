package com.galaxii.front.action.community_event;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dto.EventCalendar;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityEvent;
import com.galaxii.common.service.CommunityEventService;
import com.galaxii.common.service.CommunityService;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.action.AbstractAction;

public class IndexAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int DISP_EVENT_NUM_PAR_PAGE = 100;
	
	@Resource
	private CommunityService communityService;
	@Resource
	private CommunityEventService communityEventService;
	@Resource
	private CommunityDao communityDao;
	
	private Community community;
	
	private SimplePager<CommunityEvent> communityEvents;
	
	private EventCalendar eventCalendar;
	
	private Integer communityId;
	private String yearMonth;
	
	@Action(value="community-event/index-ajax/{communityId}/{yearMonth}",
		results={
	        @Result(name="success", location="community-event/_index.ftl")
		}
	)
	@Override
	public String execute() throws Exception {
		// community 
		community = communityDao.findById(communityId);
		if(community == null) {
			return ERROR;
		}
		eventCalendar = new EventCalendar(DateUtils.parseDate(yearMonth, "yyyy-MM"));
		communityEvents = communityEventService.paginateByMonth(
				communityId, DISP_EVENT_NUM_PAR_PAGE, eventCalendar);
		return SUCCESS;
	}
	
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	
	public Community getCommunity() {
		return community;
	}
	
	public SimplePager<CommunityEvent> getCommunityEvents() {
		return communityEvents;
	}
	
	public EventCalendar getEventCalendar() {
		return eventCalendar;
	}
}
