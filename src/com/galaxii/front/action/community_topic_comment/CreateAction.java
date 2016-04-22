package com.galaxii.front.action.community_topic_comment;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityTopicCommentDao;
import com.galaxii.common.dao.CommunityTopicDao;
import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.CommunityTopic;
import com.galaxii.common.service.CommentService;
import com.galaxii.common.service.CommunityActivityService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.CommentForm;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommentService commentService;
	@Resource
	private CommunityUserDao communityUserDao;
	@Resource
	private CommunityTopicDao communityTopicDao;
	@Resource
	private CommunityTopicCommentDao communityTopicCommentDao;
	@Resource
	private CommunityActivityService communityAvtivityService;
	@Resource
	private CommentForm commentForm;
	
	private CommentEntity comment;

	@Action(value="community-topic-comment/create-ajax",
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
        // is exists community topic??
        CommunityTopic communityTopic = communityTopicDao
        		.findById(commentForm.getBaseId());
        if(communityTopic == null) {
        	return ERROR;
        }
        // is user member??
        if(!communityUserDao.isExistCommunityUser(
        		communityTopic.getCommunityId(), getCurrentUser().getId())) {
        	return REQUIRE_COMMUNITY_MEMBER;
        }
        // create comment
        comment = commentService.comment(
        		communityTopicDao,
        		communityTopicCommentDao,
        		commentForm.getBaseId(), 
        		commentForm.getDescription(), 
        		getCurrentUser());
        // user info
        userInformationService.insertCommunityTopicComment(
        		communityTopic, getCurrentUser());
        // clear
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
		return "/community-topic-comment/create-ajax";
	}
}