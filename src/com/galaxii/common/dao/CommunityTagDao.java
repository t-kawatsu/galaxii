package com.galaxii.common.dao;

import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.dto.HotWord;
import com.galaxii.common.entity.CommunityTag;

@Repository
@Transactional
public class CommunityTagDao extends AbstractDao<CommunityTag> {

	public void deleteByCommunityId(Integer id) {
		getSession().createQuery(
			"DELETE FROM CommunityTag WHERE id.communityId = :id" )
			.setInteger( "id", id )
			.executeUpdate();
	}
	
	public List<CommunityTag> findByCommunityId(Integer id) {
		return resultList(getSession().createQuery(
			"FROM CommunityTag WHERE id.communityId = :id" )
			.setInteger( "id", id ));
	}
	
	@SuppressWarnings("unchecked")
	@Cacheable( value = "community", key = "#root.methodName")
	public List<HotWord> findByHotWord(int limit ) {
		return getSession().createQuery(
				"SELECT ct.id.name as name, count(*) as count FROM CommunityTag ct GROUP BY ct.id.name ORDER BY count(*) DESC")
				.setResultTransformer(Transformers.aliasToBean(HotWord.class))
				.setMaxResults(limit).list();
	}
}
