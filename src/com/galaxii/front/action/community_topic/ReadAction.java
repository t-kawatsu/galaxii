package com.galaxii.front.action.community_topic;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityTopicCommentDao;
import com.galaxii.common.dao.CommunityTopicCommentLikeDao;
import com.galaxii.common.dao.CommunityTopicDao;
import com.galaxii.common.dao.CommunityTopicLikeDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityTopic;
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
	private CommunityTopicDao communityTopicDao;
	@Resource
	private CommunityTopicLikeDao communityTopicLikeDao;
	@Resource
	private CommunityTopicCommentDao commentDao;
	@Resource
	private CommunityTopicCommentLikeDao commentLikeDao;
	@Resource
	private CommentForm commentForm;

	private Integer id;
	private CommunityTopic communityTopic;
	private Community community;
	private SimplePager<CommentEntity> comments;
	
	@Action(value="community-topic/read/{id}",
		results={
			@Result(name="success", location="community-topic/read.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		communityTopic = communityTopicDao.findById(id);
		if(communityTopic == null) {
			return ERROR;
		}
		if(getIsLogined()) {
			communityTopicLikeDao.fetchUserLikeState(
					communityTopic, getCurrentUser().getId());
		}
		
		community = communityDao.findById(
				communityTopic.getCommunityId());

		comments = CommentService.factorySessionPaginator(
				sessUtil, commentDao, id, DISP_COMMENT_NUM_PAR_PAGE)
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

	public CommunityTopic getCommunityTopic() {
		return communityTopic;
	}

	public SimplePager<CommentEntity> getCommunityTopicComments() {
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