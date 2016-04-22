package com.galaxii.front.action.community_recommend_comment;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityRecommendCommentDao;
import com.galaxii.common.dao.CommunityRecommendDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.front.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityRecommendCommentDao commentDao;
	@Resource
	private CommunityRecommendDao recommendDao;	
	
	private Integer id;

	@Action(value="community-recommend-comment/delete-ajax/{id}",
		results={
            @Result(name="success", location="community-recommend/_delete-comment-complete.ftl")
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
        userInformationService.deleteCommunityRecommendComment(
        		recommendDao.findById(comment.getBaseId()), getCurrentUser());
		return SUCCESS;
    }

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
}