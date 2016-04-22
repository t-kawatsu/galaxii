package com.galaxii.common.entity;

import java.util.Date;

public interface LikeEntity {
	public AbstractEmbeddable getId();
	public Date getCreatedAt();
	public void setCreatedAt(Date createdAt);
	public User getUser();
	public void setUser(User user);
}
