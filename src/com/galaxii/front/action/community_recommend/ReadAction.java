package com.galaxii.front.action.community_recommend;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityRecommendCommentDao;
import com.galaxii.common.dao.CommunityRecommendCommentLikeDao;
import com.galaxii.common.dao.CommunityRecommendDao;
import com.galaxii.common.dao.CommunityRecommendLikeDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityRecommend;
import com.galaxii.common.service.CommentService;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.CommentForm;

public class ReadAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int DISP_COMMENT_NUM_PAR_PAGE = 10;

	@Resource
	private CommunityDao communityDao;
	@Resource
	private CommunityRecommendDao communityRecommendDao;
	@Resource
	private CommunityRecommendLikeDao communityRecommendLikeDao;
	@Resource
	private CommunityRecommendCommentDao communityRecommendCommentDao;
	@Resource
	private CommunityRecommendCommentLikeDao commentLikeDao;
	@Resource
	private CommentForm commentForm;

	private Integer id;
	private CommunityRecommend communityRecommend;
	private Community community;
	private SimplePager<CommentEntity> comments;
	
	@Action(value="community-recommend/read-ajax/{id}",
		results={
			@Result(name="success", location="community-recommend/read.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		communityRecommend = communityRecommendDao.findById(id);
		if(communityRecommend == null) {
			return ERROR;
		}
		if(getIsLogined()) {
			communityRecommendLikeDao.fetchUserLikeState(
					communityRecommend, getCurrentUser().getId());
		}
		
		community = communityDao.findById(
				communityRecommend.getCommunityId());
		
		comments = CommentService.factorySessionPaginator(
				sessUtil, communityRecommendCommentDao, id, DISP_COMMENT_NUM_PAR_PAGE)
				.clearPage().paginate();
		if(getIsLogined()) {
			commentLikeDao.fetchUserLikeState(comments.getItems(), getCurrentUser().getId());
		}
		
		commentForm.setBaseId(id);
		return SUCCESS;
    }

	public void setId(Integer id) {
		this.id = id;
	}

	public CommunityRecommend getCommunityRecommend() {
		return communityRecommend;
	}

	public SimplePager<CommentEntity> getCommunityRecommendComments() {
		return comments;
	}
	
	public List<CommentEntity> getComments() {
		return comments != null ? comments.getItems() : null;
	}
	
	public Community getCommunity() {
		return community;
	}
	
	public CommentForm getCommentForm() {
		return commentForm;
	}
}