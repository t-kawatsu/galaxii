package com.galaxii.common.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.UserImage;

@Repository
@Transactional
public class UserImageDao extends AbstractDao<UserImage>
implements LikableEntityDao {
	
	@Override
	public void likeCountUp(Integer id) {
		getSession().createQuery(
			"UPDATE UserImage SET likeCnt = likeCnt + 1 WHERE id = :id" )
			.setInteger( "id", id )
			.executeUpdate();
	}

	@Override
	public void likeCountDown(Integer id) {
		getSession().createQuery(
			"UPDATE UserImage SET likeCnt = likeCnt - 1 WHERE id = :id" )
			.setInteger( "id", id )
			.executeUpdate();
	}
}
