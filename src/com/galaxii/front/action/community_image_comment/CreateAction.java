package com.galaxii.front.action.community_image_comment;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityImageCommentDao;
import com.galaxii.common.dao.CommunityImageDao;
import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.CommunityImage;
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
	private CommunityImageDao communityImageDao;
	@Resource
	private CommunityImageCommentDao commentDao;
	@Resource
	private CommentService commentService;
	@Resource
	private CommentForm commentForm;
	
	private CommentEntity comment;

	@Action(value="community-image-comment/create-ajax",
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
        CommunityImage communityImage = communityImageDao
        		.findById(commentForm.getBaseId());
        if(communityImage == null) {
        	return ERROR;
        }
        if(!communityUserDao.isExistCommunityUser(
        		communityImage.getCommunityId(), getCurrentUser().getId())) {
        	return REQUIRE_COMMUNITY_MEMBER;
        }
        // コメント作成
        comment = commentService.comment(
        		communityImageDao,
        		commentDao,
        		commentForm.getBaseId(), 
        		commentForm.getDescription(), 
        		getCurrentUser());
        // user info
        userInformationService.insertCommunityImageComment(
        		communityImage, getCurrentUser());
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
		return "/community-image-comment/create-ajax";
	}
}