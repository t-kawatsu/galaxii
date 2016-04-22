package com.galaxii.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CommunityTopicCommentLikeId extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer communityTopicCommentId;
	private Integer userId;

	@Column(name = "community_topic_comment_id")
	public Integer getCommunityTopicCommentId() {
		return communityTopicCommentId;
	}

	public void setCommunityTopicCommentId(Integer communityTopicCommentId) {
		this.communityTopicCommentId = communityTopicCommentId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
