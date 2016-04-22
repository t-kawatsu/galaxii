package com.galaxii.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserWatchId extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer fromUserId;
	private Integer toUserId;
	
	@Column(name="from_user_id")
	public Integer getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
	@Column(name="to_user_id")
	public Integer getToUserId() {
		return toUserId;
	}
	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

}
