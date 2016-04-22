package com.galaxii.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CommunityTopicLikeId extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer communityTopicId;
	private Integer userId;

	@Column(name = "community_topic_id")
	public Integer getCommunityTopicId() {
		return communityTopicId;
	}

	public void setCommunityTopicId(Integer communityTopicId) {
		this.communityTopicId = communityTopicId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
