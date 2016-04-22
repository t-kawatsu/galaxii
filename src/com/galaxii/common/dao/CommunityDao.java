package com.galaxii.common.dao;

import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.dto.CategorizedCommunity;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityCategory;

@Repository
@Transactional
public class CommunityDao extends AbstractDao<Community>
implements CommentableEntityDao {
	
	private static final String CACHE_NAME = "community";
	
	public Community findById(Integer id) {
		if(id == null) {
			return null;
		}
		return (Community)getSession().createQuery(
				"FROM Community c LEFT JOIN fetch c.user LEFT JOIN fetch c.communityImage WHERE c.id = :id")
				.setInteger("id", id).uniqueResult();
	}

	@Override
	public void commentCountUp(Integer id) {
		getSession().createQuery(
				"UPDATE Community SET commentCnt = commentCnt + 1 WHERE id = :id" )
				.setInteger( "id", id )
				.executeUpdate();
	}

	@Override
	public void commentCountDown(Integer id) {
		getSession().createQuery(
				"UPDATE Community SET commentCnt = commentCnt - 1 WHERE id = :id" )
				.setInteger( "id", id )
				.executeUpdate();
	}
	
	@Cacheable( value = CACHE_NAME, key = "#root.methodName")
	public List<Community> findOrderByUsersCnt(int limit) {
		return resultList(getSession().createQuery(
				"FROM Community c INNER JOIN fetch c.user ORDER BY c.communityUsersCnt DESC")
				.setMaxResults(limit));
	}
	
	@Cacheable( value = CACHE_NAME, key = "#root.methodName")
	public List<Community> findOrderByNew(int limit) {
		return resultList(getSession().createQuery(
				"FROM Community c INNER JOIN fetch c.user ORDER BY c.createdAt DESC")
				.setMaxResults(limit));
	}
	
	@SuppressWarnings("unchecked")
	@Cacheable( value = CACHE_NAME, key = "#root.methodName")
	public List<CategorizedCommunity> findHotCommunityCategories(int limit ) {
		return getSession().createQuery(
				"SELECT c.categoryId as categoryId, count(*) as count FROM Community c GROUP BY c.categoryId ORDER BY count(*) DESC")
				.setMaxResults(limit)
				.setResultTransformer(Transformers.aliasToBean(CategorizedCommunity.class))
				.list();
	}
	
	@Cacheable( value = CACHE_NAME, key = "#root.methodName + '_' + #root.args[0]")
	public List<Community> findOrderByUsersCntByCategory(CommunityCategory category, int limit) {
		return resultList(getSession().createQuery(
				"FROM Community c WHERE c.categoryId = :categoryId ORDER BY c.communityUsersCnt DESC")
				.setParameter("categoryId", category)
				.setMaxResults(limit));
	}
}
