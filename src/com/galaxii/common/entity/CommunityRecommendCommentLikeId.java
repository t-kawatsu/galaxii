package com.galaxii.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CommunityRecommendCommentLikeId extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer communityRecommendCommentId;
	private Integer userId;

	@Column(name = "community_recommend_comment_id")
	public Integer getCommunityRecommendCommentId() {
		return communityRecommendCommentId;
	}

	public void setCommunityRecommendCommentId(Integer communityRecommendCommentId) {
		this.communityRecommendCommentId = communityRecommendCommentId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
