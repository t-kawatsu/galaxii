package com.galaxii.front.action.community_event_comment;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityEventCommentDao;
import com.galaxii.common.dao.CommunityEventDao;
import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.CommunityEvent;
import com.galaxii.common.service.CommentService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.CommentForm;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityUserDao communityUserDao;
	@Resource
	private CommunityEventDao communityEventDao;
	@Resource
	private CommunityEventCommentDao commentDao;
	@Resource
	private CommentService commentService;
	@Resource
	private CommentForm commentForm;
	
	private CommentEntity comment;

	@Action(value="community-event-comment/create-ajax",
		results={
			@Result(name="input", location="common/_comment-form.ftl"),
            @Result(name="success", location="common/_comment-form.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!commentForm.validate(this)) {
            return INPUT;
        }
        CommunityEvent communityEvent = communityEventDao
        		.findById(commentForm.getBaseId());
        if(communityEvent == null) {
        	return ERROR;
        }
        if(!communityUserDao.isExistCommunityUser(
        		communityEvent.getCommunityId(), getCurrentUser().getId())) {
        	return REQUIRE_COMMUNITY_MEMBER;
        }
        // コメント作成
        comment = commentService.comment(
        		communityEventDao,
        		commentDao,
        		commentForm.getBaseId(), 
        		commentForm.getDescription(), 
        		getCurrentUser());
        // user info
        userInformationService.insertCommunityEventComment(
        		communityEventDao.findById(commentForm.getBaseId()), getCurrentUser());
        // クリア
        commentForm.setDescription(null); 
		return SUCCESS;
    }
	
	public CommentEntity getCreatedComment() {
		return comment;
	}
	
	public CommentForm getCommentForm() {
		return commentForm;
	}
	
	public String getCommentFormAction() {
		return "/community-event-comment/create-ajax";
	}
}