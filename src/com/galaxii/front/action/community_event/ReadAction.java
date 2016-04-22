package com.galaxii.front.action.community_event;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityEventCommentDao;
import com.galaxii.common.dao.CommunityEventCommentLikeDao;
import com.galaxii.common.dao.CommunityEventDao;
import com.galaxii.common.dao.CommunityEventLikeDao;
import com.galaxii.common.dao.CommunityEventUserDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityEvent;
import com.galaxii.common.entity.CommunityEventUser;
import com.galaxii.common.service.CommentService;
import com.galaxii.common.service.CommunityEventService;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.CommentForm;

public class ReadAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int DISP_COMMENT_NUM_PAR_PAGE = 10;
	
	private int DISP_EVENT_JOINED_USERS = 15;
	
	@Resource
	private CommunityEventService communityEventService;
	@Resource
	private CommunityEventDao communityEventDao;
	@Resource
	private CommunityEventLikeDao communityEventLikeDao;
	@Resource
	private CommunityDao communityDao;
	@Resource
	private CommunityEventCommentDao communityEventCommentDao;
	@Resource
	private CommunityEventCommentLikeDao commentLikeDao;
	@Resource
	private CommunityEventUserDao communityEventUserDao;
	@Resource
	private CommentForm commentForm;

	private Integer id;
	private boolean isUserJoined;
	private CommunityEvent communityEvent;
	private Community community;
	private SimplePager<CommentEntity> comments;
	private SimplePager<CommunityEventUser> communityEventUsers;
	
	@Action(value="community-event/read/{id}",
		results={
			@Result(name="success", location="community-event/read.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		// event
		communityEvent = communityEventDao.findById(id);
		if(communityEvent == null) {
			return ERROR;
		}
		if(getIsLogined()) {
			communityEventLikeDao.fetchUserLikeState(
					communityEvent, getCurrentUser().getId());
		}
		
		// community
		community = communityDao.findById(
				communityEvent.getCommunityId());
		
		// comments
		comments = CommentService.factorySessionPaginator(
				sessUtil, communityEventCommentDao, id, DISP_COMMENT_NUM_PAR_PAGE)
				.clearPage().paginate();
		if(getIsLogined()) {
			commentLikeDao.fetchUserLikeState(comments.getItems(), getCurrentUser().getId());
		}
		commentForm.setBaseId(id);
		
		// joined users
		communityEventUsers = communityEventUserDao
			.paginateByCommunityEventId(id, DISP_EVENT_JOINED_USERS, 1);
		
		if(getIsLogined()) {
			isUserJoined = communityEventUserDao
					.isExistCommunityEventUser(id, getCurrentUser().getId());
		}
		
		return SUCCESS;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}

	public CommunityEvent getCommunityEvent() {
		return communityEvent;
	}

	public SimplePager<CommentEntity> getCommunityEventComments() {
		return comments;
	}
	
	public List<CommentEntity> getComments() {
		return comments != null ? comments.getItems() : null;
	}
	
	public Community getCommunity() {
		return community;
	}
	
	public CommentForm getCommentForm() {
		return commentForm;
	}
	
	public boolean getIsUserJoined() {
		return isUserJoined;
	}
	
	public SimplePager<CommunityEventUser> getCommunityEventUsers() {
		return communityEventUsers;
	}
	
	public Date getToday() {
		return new Date();
	}
}