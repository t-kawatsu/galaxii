package com.galaxii.common.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.CommunityEventComment;
import com.galaxii.common.entity.Status;
import com.galaxii.common.entity.User;

@Repository
@Transactional
public class CommunityEventCommentDao extends AbstractDao<CommunityEventComment>
	implements LikableEntityDao, CommentDao<CommunityEventComment> {

	public CommentEntity comment(
			Integer baseId, String description, User user) {
		CommunityEventComment cc = new CommunityEventComment();
		cc.setCommunityEventId(baseId);
		cc.setDescription(description);
		cc.setLikeCnt(0);
		cc.setStatus(Status.LIVE);
		cc.setUserId(user.getId());
		cc.setUser(user);
		getSession().save(cc);
		return cc;
	}

	@Override
	public void likeCountUp(Integer id) {
		getSession().createQuery(
			"UPDATE CommunityEventComment SET likeCnt = likeCnt + 1 WHERE id = :id" )
			.setInteger( "id", id )
			.executeUpdate();
	}

	@Override
	public void likeCountDown(Integer id) {
		getSession().createQuery(
			"UPDATE CommunityEventComment SET likeCnt = likeCnt - 1 WHERE id = :id" )
			.setInteger( "id", id )
			.executeUpdate();
	}

	@Override
	public void delete(CommentEntity comment) {
		comment.setStatus(Status.DELETED);
		update(comment);
	}

	@Override
	public String getBaseEntityIdRefer() {
		return "communityEventId";
	}
}
