package com.galaxii.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.CommunityImage;
import com.galaxii.common.entity.Status;

@Repository
@Transactional
public class CommunityImageDao extends AbstractDao<CommunityImage>
implements LikableEntityDao, CommentableEntityDao {
	
	public List<CommunityImage> findByCommunityId(Integer id) {
		return this.resultList(getSession().createQuery(
			"FROM CommunityImage ci WHERE ci.communityId = :id")
			.setInteger("id", id));
	}

	public CommunityImage findById(Integer id) {
		if(id == null) {
			return null;
		}
		return (CommunityImage)getSession().createQuery(
				"FROM CommunityImage ci INNER JOIN fetch ci.user WHERE ci.id = :id")
				.setInteger("id", id).uniqueResult();
	}
	
	public void delete(CommunityImage image) {
		image.setStatus(Status.DELETED);
		update(image);
	}
	
	public void deleteById(Integer id) {
		CommunityImage image = findById(id);
		if(image != null) {
			delete(image);
		}
	}

	@Override
	public void likeCountUp(Integer id) {
		getSession().createQuery(
			"UPDATE CommunityImage SET likeCnt = likeCnt + 1 WHERE id = :id" )
			.setInteger( "id", id )
			.executeUpdate();
	}

	@Override
	public void likeCountDown(Integer id) {
		getSession().createQuery(
			"UPDATE CommunityImage SET likeCnt = likeCnt - 1 WHERE id = :id" )
			.setInteger( "id", id )
			.executeUpdate();
	}
	
	@Override
	public void commentCountUp(Integer id) {
		getSession().createQuery(
				"UPDATE CommunityImage SET commentCnt = commentCnt + 1 WHERE id = :id" )
				.setInteger( "id", id )
				.executeUpdate();
	}

	@Override
	public void commentCountDown(Integer id) {
		getSession().createQuery(
				"UPDATE CommunityImage SET commentCnt = commentCnt - 1 WHERE id = :id" )
				.setInteger( "id", id )
				.executeUpdate();
	}
}
