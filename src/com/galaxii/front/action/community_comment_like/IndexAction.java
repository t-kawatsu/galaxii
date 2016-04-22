package com.galaxii.front.action.community_comment_like;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityCommentLikeDao;
import com.galaxii.common.entity.LikeEntity;
import com.galaxii.common.service.LikeService;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.action.AbstractAction;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int DISP_USER_NUM_PAR_PAGE = 15;
	
	@Resource
	private LikeService likeService;
	@Resource
	private CommunityCommentLikeDao likeDao;
	
	private Integer id;
	private SimplePager<LikeEntity> likedUsers;

	@Action(value="community-comment-like/index-ajax/{id}",
		results={
			@Result(name="success", location="common/_like-users-ajax.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		likedUsers = likeService.factorySessionPaginator(
				sessUtil, likeDao, id, DISP_USER_NUM_PAR_PAGE)
				.clearPage().paginate();
		return SUCCESS;
    }
	
	@Action(value="community-comment-like/more-ajax/{id}",
			results={
	            @Result(name="success", location="common/_like-users.ftl")
			}
	)
    public String moreAction() throws Exception {
		likedUsers = likeService.factorySessionPaginator(
				sessUtil, likeDao, id, DISP_USER_NUM_PAR_PAGE).paginate();
		return SUCCESS;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public SimplePager<LikeEntity> getLikedUsers() {
		return likedUsers;
	}
}