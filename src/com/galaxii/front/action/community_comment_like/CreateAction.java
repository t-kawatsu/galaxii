package com.galaxii.front.action.community_comment_like;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityCommentDao;
import com.galaxii.common.dao.CommunityCommentLikeDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.User;
import com.galaxii.common.service.LikeService;
import com.galaxii.front.action.AbstractAction;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private LikeService likeService;
	@Resource
	private CommunityCommentDao commentDao;
	@Resource
	private CommunityCommentLikeDao likeDao;
	
	private Integer id;
	private Map<String, Object> uploadResultJson
		= new HashMap<String, Object>();

	@Action(value="community-comment-like/create-json/{id}",
		results={
			@Result(name="success", type="json", params={
					"statusCode", "200",
					"contentType", "application/json",
					"noCache", "true",
					"root", "uploadResultJson"
					}),
			@Result(name="login", type="json", params={
					"statusCode", "401",
					"contentType", "application/json",
					"noCache", "true",
					"root", "uploadResultJson"
					})
		}
	)
	@Override
    public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
        CommentEntity commentEntity = commentDao.findById(id);
        if(commentEntity == null) {
        	return ERROR;
        }
        User user = getCurrentUser();
        uploadResultJson = likeService.like(commentDao, likeDao, id, user.getId());
        // user info
        userInformationService.insertCommunityCommentLike(commentEntity, user);
		return SUCCESS;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Map<String,Object> getUploadResultJson() {
		return uploadResultJson;
	}
}