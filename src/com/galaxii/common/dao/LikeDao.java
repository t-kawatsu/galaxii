package com.galaxii.common.dao;

import java.util.List;

import com.galaxii.common.entity.LikableEntity;

public interface LikeDao {
	public void create(Integer baseEntityId, Integer userId);
	public void deleteById(Integer baseEntityId, Integer userId);
	public boolean isLiked(Integer baseEntityId, Integer userId);
	public int countByBaseId(Integer baseEntityId);
	public <B extends LikableEntity> List<B> fetchUserLikeState(List<B> likableEntities, Integer userId);
	public <B extends LikableEntity> B fetchUserLikeState(B likableEntity, Integer userId);
	public String getBaseEntityIdRefer();
}
