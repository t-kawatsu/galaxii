package com.galaxii.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CommunityRecommendLikeId extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer communityRecommendId;
	private Integer userId;

	@Column(name = "community_recommend_id")
	public Integer getCommunityRecommendId() {
		return communityRecommendId;
	}

	public void setCommunityRecommendId(Integer communityRecommendId) {
		this.communityRecommendId = communityRecommendId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
