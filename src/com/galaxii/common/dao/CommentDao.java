package com.galaxii.common.dao;

import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.User;

public interface CommentDao<T extends CommentEntity> {
	public CommentEntity comment(Integer baseId, String description, User user);
	public void delete(CommentEntity comment);
	public String getBaseEntityIdRefer();
}
