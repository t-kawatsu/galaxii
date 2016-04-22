package com.galaxii.common.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.CommunityRecommend;
import com.galaxii.common.entity.Status;

@Repository
@Transactional
public class CommunityRecommendDao extends AbstractDao<CommunityRecommend>
implements LikableEntityDao, CommentableEntityDao {

	public CommunityRecommend findById(Integer id) {
		if(id == null) {
			return null;
		}
		return (CommunityRecommend)getSession().createQuery(
				"FROM CommunityRecommend cr INNER JOIN fetch cr.user LEFT JOIN fetch cr.recommendCommunity WHERE cr.id = :id")
				.setInteger("id", id).uniqueResult();
	}
	
	public void delete(CommunityRecommend recomment) {
		recomment.setStatus(Status.DELETED);
		update(recomment);
	}

	@Override
	public void likeCountUp(Integer id) {
		getSession().createQuery(
			"UPDATE CommunityRecommend SET likeCnt = likeCnt + 1 WHERE id = :id" )
			.setInteger( "id", id )
			.executeUpdate();
	}

	@Override
	public void likeCountDown(Integer id) {
		getSession().createQuery(
			"UPDATE CommunityRecommend SET likeCnt = likeCnt - 1 WHERE id = :id" )
			.setInteger( "id", id )
			.executeUpdate();
	}
	
	@Override
	public void commentCountUp(Integer id) {
		getSession().createQuery(
				"UPDATE CommunityRecommend SET commentCnt = commentCnt + 1 WHERE id = :id" )
				.setInteger( "id", id )
				.executeUpdate();
	}

	@Override
	public void commentCountDown(Integer id) {
		getSession().createQuery(
				"UPDATE CommunityRecommend SET commentCnt = commentCnt - 1 WHERE id = :id" )
				.setInteger( "id", id )
				.executeUpdate();
	}
}
