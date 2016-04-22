package com.galaxii.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CommunityEventCommentLikeId extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer communityEventCommentId;
	private Integer userId;

	@Column(name = "community_event_comment_id")
	public Integer getCommunityEventCommentId() {
		return communityEventCommentId;
	}

	public void setCommunityEventCommentId(Integer communityEventCommentId) {
		this.communityEventCommentId = communityEventCommentId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
