package com.galaxii.front.action.community_image;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityImageCommentDao;
import com.galaxii.common.dao.CommunityImageCommentLikeDao;
import com.galaxii.common.dao.CommunityImageDao;
import com.galaxii.common.dao.CommunityImageLikeDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityImage;
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
	private CommunityImageDao communityImageDao;
	@Resource
	private CommunityImageLikeDao communityImageLikeDao;
	@Resource
	private CommunityImageCommentDao communityImageCommentDao;
	@Resource
	private CommunityImageCommentLikeDao commentLikeDao;
	@Resource
	private CommentForm commentForm;

	private Integer id;
	private CommunityImage communityImage;
	private Community community;
	private SimplePager<CommentEntity> comments;
	
	@Action(value="community-image/read-ajax/{id}",
		results={
			@Result(name="success", location="community-image/read.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		// image
		communityImage = communityImageDao.findById(id);
		if(communityImage == null) {
			return ERROR;
		}
		if(getIsLogined()) {
			communityImageLikeDao.fetchUserLikeState(
					communityImage, getCurrentUser().getId());
		}
		
		community = communityDao.findById(
				communityImage.getCommunityId());
		
		comments = CommentService.factorySessionPaginator(
				sessUtil, communityImageCommentDao, id, DISP_COMMENT_NUM_PAR_PAGE)
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

	public CommunityImage getCommunityImage() {
		return communityImage;
	}

	public SimplePager<CommentEntity> getCommunityImageComments() {
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