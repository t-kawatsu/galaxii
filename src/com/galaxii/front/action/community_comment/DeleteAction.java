package com.galaxii.front.action.community_comment;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityActivityDao;
import com.galaxii.common.dao.CommunityCommentDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.CommunityContentsCategory;
import com.galaxii.front.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityCommentDao commentDao;
	@Resource
	private CommunityActivityDao communityAvtivityDao;
	
	private Integer id;

	@Action(value="community-comment/delete-ajax/{id}",
		results={
            @Result(name="success", location="community/_delete-comment-complete.ftl")
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
        
        // アクティビティ削除
        communityAvtivityDao.deleteActivity(
        		CommunityContentsCategory.COMMENT, id);
        // user info
        userInformationService.deleteCommunityComment(comment, getCurrentUser());
		return SUCCESS;
    }

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
}