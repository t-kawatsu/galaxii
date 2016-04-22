package com.galaxii.front.action.community_event_comment;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityEventCommentDao;
import com.galaxii.common.dao.CommunityEventDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.front.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityEventCommentDao commentDao;
	@Resource
	private CommunityEventDao eventDao;
	
	private Integer id;

	@Action(value="community-event-comment/delete-ajax/{id}",
		results={
            @Result(name="success", location="community-event/_delete-comment-complete.ftl")
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
        userInformationService.deleteCommunityEventComment(
        		eventDao.findById(comment.getBaseId()), getCurrentUser());
		return SUCCESS;
    }

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
}