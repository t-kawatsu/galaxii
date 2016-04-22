package com.galaxii.front.action.community_topic_comment;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityTopicCommentDao;
import com.galaxii.common.dao.CommunityTopicDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.front.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityTopicCommentDao commentDao;
	@Resource
	private CommunityTopicDao communityTopicDao;
	
	private Integer id;

	@Action(value="community-topic-comment/delete-ajax/{id}",
		results={
            @Result(name="success", location="community-topic/_delete-comment-complete.ftl")
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
        userInformationService.deleteCommunityTopicComment(
        		communityTopicDao.findById(comment.getBaseId()), getCurrentUser());
		return SUCCESS;
    }

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
}