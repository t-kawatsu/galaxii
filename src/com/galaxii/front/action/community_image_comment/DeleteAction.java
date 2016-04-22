package com.galaxii.front.action.community_image_comment;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityImageCommentDao;
import com.galaxii.common.dao.CommunityImageDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.front.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityImageCommentDao commentDao;
	@Resource
	private CommunityImageDao communityImageDao;
	
	private Integer id;

	@Action(value="community-image-comment/delete-ajax/{id}",
		results={
            @Result(name="success", location="community-image/_delete-comment-complete.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
        CommentEntity comment = commentDao.findById(id);
        if(comment == null || !comment.getUserId().equals(getCurrentUser().getId())) {
            return ERROR;
        }
        commentDao.delete(comment);
        // user info
        userInformationService.deleteCommunityImageComment(
        		communityImageDao.findById(comment.getBaseId()), getCurrentUser());
		return SUCCESS;
    }

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
}