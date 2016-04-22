package com.galaxii.front.action.community_recommend_comment;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityRecommendCommentDao;
import com.galaxii.common.dao.CommunityRecommendDao;
import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.CommunityRecommend;
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
	private CommunityRecommendDao communityRecommendDao;
	@Resource
	private CommunityRecommendCommentDao commentDao;
	@Resource
	private CommentService commentService;
	@Resource
	private CommentForm commentForm;
	
	private CommentEntity comment;

	@Action(value="community-recommend-comment/create-ajax",
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
        CommunityRecommend communityRecommend = communityRecommendDao
        		.findById(commentForm.getBaseId());
        if(communityRecommend == null) {
        	return ERROR;
        }
        if(!communityUserDao.isExistCommunityUser(
        		communityRecommend.getCommunityId(), getCurrentUser().getId())) {
        	return REQUIRE_COMMUNITY_MEMBER;
        }
        // コメント作成
        comment = commentService.comment(
        		communityRecommendDao,
        		commentDao,
        		commentForm.getBaseId(), 
        		commentForm.getDescription(), 
        		getCurrentUser());
        // user info
        userInformationService.insertCommunityRecommendComment(
        		communityRecommend, getCurrentUser());
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
		return "/community-recommend-comment/create-ajax";
	}
}