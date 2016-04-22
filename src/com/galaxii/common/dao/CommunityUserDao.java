package com.galaxii.common.dao;

import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.CommunityUser;
import com.galaxii.common.entity.CommunityUserId;
import com.galaxii.common.hibernate.Association;
import com.galaxii.common.util.DbSelectPaginator;
import com.galaxii.common.util.SimplePager;

@Repository
@Transactional
public class CommunityUserDao extends AbstractDao<CommunityUser> {
	
	public boolean isExistCommunityUser(Integer communityId, Integer userId) {
		Integer cnt = detectCountValue(getSession().createQuery(
			"SELECT count(*) FROM CommunityUser cu WHERE" +
			" cu.id.communityId = :communityId AND cu.id.userId = :userId ")
			.setInteger("communityId", communityId)
			.setInteger("userId", userId));
		return 0 < cnt;
	}
	
	public SimplePager<CommunityUser> paginateByCommunityId(Integer communityId, int limit, final Integer page) {
		if(communityId == null) {
			return null;
		}
		DbSelectPaginator<CommunityUser> p = 
				new DbSelectPaginator<CommunityUser>(this);
		p.addCriterion( Restrictions.eq("id.communityId", communityId) );
		p.addAssociation( Association.join("user", JoinType.INNER_JOIN) );
		p.setLimit(limit);
		p.setPage(page);
		return p.paginate();
	}
	
	public void create(Integer communityId, Integer userId) {
		CommunityUser cu = new CommunityUser();
		CommunityUserId cuId = new CommunityUserId();
		cuId.setCommunityId(communityId);
		cuId.setUserId(userId);
		cu.setId(cuId);
		persist(cu);
	}
	
	public void deleteById(Integer communityId, Integer userId) {
		CommunityUser cu = (CommunityUser)getSession().createQuery(
			"FROM CommunityUser cu WHERE" +
			" cu.id.communityId = :communityId AND cu.id.userId = :userId ")
			.setInteger("communityId", communityId)
			.setInteger("userId", userId).uniqueResult();
		if(cu == null) {
			return;
		}
		delete(cu);
	}

}
