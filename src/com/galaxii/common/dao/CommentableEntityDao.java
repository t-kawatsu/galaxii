package com.galaxii.common.dao;

public interface CommentableEntityDao {
	public void commentCountUp(Integer id);
	public void commentCountDown(Integer id);
}
