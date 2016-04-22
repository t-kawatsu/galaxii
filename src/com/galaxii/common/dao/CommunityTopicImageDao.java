package com.galaxii.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.CommunityTopicImage;

@Repository
@Transactional
public class CommunityTopicImageDao extends AbstractDao<CommunityTopicImage> {

	public List<CommunityTopicImage> findByCommunityTopicId(Integer id) {
		return this.resultList(getSession().createQuery(
			"FROM CommunityTopicImage cti WHERE cti.communityTopicId = :id")
			.setInteger("id", id));
	}
}
