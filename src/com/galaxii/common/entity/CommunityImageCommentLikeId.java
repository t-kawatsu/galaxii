package com.galaxii.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CommunityImageCommentLikeId extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer communityImageCommentId;
	private Integer userId;

	@Column(name = "community_image_comment_id")
	public Integer getCommunityImageCommentId() {
		return communityImageCommentId;
	}

	public void setCommunityImageCommentId(Integer communityImageCommentId) {
		this.communityImageCommentId = communityImageCommentId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
