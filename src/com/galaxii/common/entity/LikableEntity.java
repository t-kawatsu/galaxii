package com.galaxii.common.entity;

public interface LikableEntity {
	public Integer getId();
	public boolean getIsUserLiked();
	public void setIsUserLiked(boolean isUserLiked);
	public Integer getLikeCnt();
	public void setLikeCnt(Integer likeCnt);
}
