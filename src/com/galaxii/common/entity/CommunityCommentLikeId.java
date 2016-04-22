package com.galaxii.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CommunityCommentLikeId extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer communityCommentId;
	private Integer userId;

	@Column(name = "community_comment_id")
	public Integer getCommunityCommentId() {
		return communityCommentId;
	}

	public void setCommunityCommentId(Integer communityCommentId) {
		this.communityCommentId = communityCommentId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
