package com.galaxii.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.CommunityEventLike;
import com.galaxii.common.entity.CommunityEventLikeId;
import com.galaxii.common.entity.LikableEntity;

@Repository
@Transactional
public class CommunityEventLikeDao extends AbstractDao<CommunityEventLike> 
	implements LikeDao {
	
	public void create(Integer baseEntityId, Integer userId) {
		CommunityEventLike likeEntity = new CommunityEventLike();
		CommunityEventLikeId id = new CommunityEventLikeId();
		id.setCommunityEventId(baseEntityId);
		id.setUserId(userId);
		likeEntity.setId(id);
		save(likeEntity);
	}

	public void deleteById(Integer baseEntityId, Integer userId) {
		CommunityEventLikeId id = new CommunityEventLikeId();
		id.setCommunityEventId(baseEntityId);
		id.setUserId(userId);
		CommunityEventLike like = (CommunityEventLike)findById(id);
		delete(like);
	}
	
	public boolean isLiked(Integer baseEntityId, Integer userId) {
		return 0 < detectCountValue(getSession().createQuery(
				"SELECT COUNT(*) FROM CommunityEventLike ccl " +
				"WHERE ccl.id.communityEventId = :baseId AND ccl.id.userId = :userId ")
				.setInteger("baseId", baseEntityId)
				.setInteger("userId", userId));
	}

	@Override
	public int countByBaseId(Integer baseEntityId) {
		return detectCountValue(getSession().createQuery(
				"SELECT COUNT(*) FROM CommunityEventLike ccl " +
				"WHERE ccl.id.communityEventId = :baseId ")
				.setInteger("baseId", baseEntityId));
	}

	@Override
	public <B extends LikableEntity> List<B> fetchUserLikeState(
			List<B> likableEntities, Integer userId) {
		if(likableEntities == null) {
			return null;
		}
		for(LikableEntity row : likableEntities) {
			row.setIsUserLiked( isLiked(row.getId(), userId) );
		}
		return likableEntities;
	}
	
	@Override
	public <B extends LikableEntity> B fetchUserLikeState(B likableEntity,
			Integer userId) {
		if(likableEntity == null) {
			return null;
		}
		likableEntity.setIsUserLiked( isLiked(likableEntity.getId(), userId) );
		return likableEntity;
	}
	
	@Override
	public String getBaseEntityIdRefer() {
		return "id.communityEventId";
	}
}
