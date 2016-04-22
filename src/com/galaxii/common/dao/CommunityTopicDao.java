package com.galaxii.common.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.CommunityTopic;
import com.galaxii.common.entity.Status;

@Repository
@Transactional
public class CommunityTopicDao extends AbstractDao<CommunityTopic>
implements LikableEntityDao, CommentableEntityDao {

	public CommunityTopic findById(Integer id) {
		if(id == null) {
			return null;
		}
		return (CommunityTopic)getSession().createQuery(
				"FROM CommunityTopic ct INNER JOIN fetch ct.user LEFT JOIN fetch ct.communityTopicImages WHERE ct.id = :id")
				.setInteger("id", id).uniqueResult();
	}

	@Override
	public void likeCountUp(Integer id) {
		getSession().createQuery(
			"UPDATE CommunityTopic SET likeCnt = likeCnt + 1 WHERE id = :id" )
			.setInteger( "id", id )
			.executeUpdate();
	}

	@Override
	public void likeCountDown(Integer id) {
		getSession().createQuery(
			"UPDATE CommunityTopic SET likeCnt = likeCnt - 1 WHERE id = :id" )
			.setInteger( "id", id )
			.executeUpdate();
	}
	
	public void delete(CommunityTopic topic) {
		topic.setStatus(Status.DELETED);
		update(topic);
	}

	@Override
	public void commentCountUp(Integer id) {
		getSession().createQuery(
				"UPDATE CommunityTopic SET commentCnt = commentCnt + 1 WHERE id = :id" )
				.setInteger( "id", id )
				.executeUpdate();
	}

	@Override
	public void commentCountDown(Integer id) {
		getSession().createQuery(
				"UPDATE CommunityTopic SET commentCnt = commentCnt - 1 WHERE id = :id" )
				.setInteger( "id", id )
				.executeUpdate();
	}
}
