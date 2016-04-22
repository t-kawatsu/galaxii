package com.galaxii.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CommunityImageLikeId extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer communityImageId;
	private Integer userId;

	@Column(name = "community_image_id")
	public Integer getCommunityImageId() {
		return communityImageId;
	}

	public void setCommunityImageId(Integer communityImageId) {
		this.communityImageId = communityImageId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
