package com.galaxii.common.entity;

import java.util.Date;

public interface CommentEntity extends LikableEntity {
	public Integer getId();
	public void setId(Integer id);
	public Integer getUserId();
	public void setUserId(Integer userId);
	public String getDescription();
	public void setDescription(String description);
	public Integer getLikeCnt();
	public void setLikeCnt(Integer likeCnt);
	public Date getCreatedAt();
	public void setCreatedAt(Date createdAt);
	public Date getUpdatedAt();
	public void setUpdatedAt(Date updatedAt);
	public Status getStatus();
	public void setStatus(Status status);
	public Integer getBaseId();
	public void setBaseId(Integer baseId);
	public User getUser();
	public void setUser(User user);
	public boolean getIsUserLiked();
	public void setIsUserLiked(boolean isUserLiked);
}
