package com.galaxii.front.action.community_comment;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityCommentDao;
import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.entity.CommentEntity;
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
	private CommunityUserDao communityUserDao;
	@Resource
	private CommentService commentService;
	@Resource
	private CommunityCommentDao communityCommentDao;
	@Resource
	private CommunityDao communityDao;
	@Resource
	private CommunityActivityService communityAvtivityService;
	@Resource
	private CommentForm commentForm;
	
	private CommentEntity comment;

	@Action(value="community-comment/create-ajax",
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
        if(!communityUserDao.isExistCommunityUser(
        		commentForm.getBaseId(), getCurrentUser().getId())) {
        	return REQUIRE_COMMUNITY_MEMBER;
        }
        // コメント作成
        comment = commentService.comment(
        		communityDao,
        		communityCommentDao,
        		commentForm.getBaseId(), 
        		commentForm.getDescription(), 
        		getCurrentUser());
        // アクティビティ設定
        communityAvtivityService.insertCommentActivity(comment);
        // user info
        userInformationService.insertCommunityComment(comment, getCurrentUser());
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
}