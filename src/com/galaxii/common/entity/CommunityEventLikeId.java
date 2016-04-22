package com.galaxii.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CommunityEventLikeId extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer communityEventId;
	private Integer userId;

	@Column(name = "community_event_id")
	public Integer getCommunityEventId() {
		return communityEventId;
	}

	public void setCommunityEventId(Integer communityEventId) {
		this.communityEventId = communityEventId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
