package com.galaxii.common.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.CommunityEvent;
import com.galaxii.common.entity.Status;

@Repository
@Transactional
public class CommunityEventDao extends AbstractDao<CommunityEvent>
implements LikableEntityDao, CommentableEntityDao {

	public CommunityEvent findById(Integer id) {
		if(id == null) {
			return null;
		}
		return (CommunityEvent)getSession().createQuery(
				"FROM CommunityEvent ce INNER JOIN fetch ce.user WHERE ce.id = :id")
				.setInteger("id", id).uniqueResult();
	}

	@Override
	public void likeCountUp(Integer id) {
		getSession().createQuery(
			"UPDATE CommunityEvent SET likeCnt = likeCnt + 1 WHERE id = :id" )
			.setInteger( "id", id )
			.executeUpdate();
	}

	@Override
	public void likeCountDown(Integer id) {
		getSession().createQuery(
			"UPDATE CommunityEvent SET likeCnt = likeCnt - 1 WHERE id = :id" )
			.setInteger( "id", id )
			.executeUpdate();
	}
	
	@Override
	public void commentCountUp(Integer id) {
		getSession().createQuery(
				"UPDATE CommunityEvent SET commentCnt = commentCnt + 1 WHERE id = :id" )
				.setInteger( "id", id )
				.executeUpdate();
	}

	@Override
	public void commentCountDown(Integer id) {
		getSession().createQuery(
				"UPDATE CommunityEvent SET commentCnt = commentCnt - 1 WHERE id = :id" )
				.setInteger( "id", id )
				.executeUpdate();
	}
	
	public void delete(CommunityEvent entity) {
		entity.setStatus(Status.DELETED);
		update(entity);
	}

}
