package com.galaxii.common.dao;

public interface LikableEntityDao {
	public void likeCountUp(Integer id);
	public void likeCountDown(Integer id);
}
