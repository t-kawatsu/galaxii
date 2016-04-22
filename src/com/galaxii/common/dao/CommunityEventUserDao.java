package com.galaxii.common.dao;

import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.CommunityEventUser;
import com.galaxii.common.entity.CommunityEventUserId;
import com.galaxii.common.hibernate.Association;
import com.galaxii.common.util.DbSelectPaginator;
import com.galaxii.common.util.SimplePager;

@Repository
@Transactional
public class CommunityEventUserDao extends AbstractDao<CommunityEventUser> {
	
	public boolean isExistCommunityEventUser(Integer communityEventId, Integer userId) {
		Integer cnt = detectCountValue(getSession().createQuery(
			"SELECT count(*) FROM CommunityEventUser ceu WHERE" +
			" ceu.id.communityEventId = :communityEventId AND ceu.id.userId = :userId ")
			.setInteger("communityEventId", communityEventId)
			.setInteger("userId", userId));
		return 0 < cnt;
	}
	
	public void deleteById(Integer communityEventId, Integer userId) {
		CommunityEventUser ceu = (CommunityEventUser)getSession().createQuery(
			"FROM CommunityEventUser ceu WHERE" +
			" ceu.id.communityEventId = :communityEventId AND ceu.id.userId = :userId ")
			.setInteger("communityEventId", communityEventId)
			.setInteger("userId", userId).uniqueResult();
		if(ceu == null) {
			return;
		}
		delete(ceu);
	}

	public SimplePager<CommunityEventUser> paginateByCommunityEventId(
			Integer communityEventId, int limit, final Integer page) {
		if(communityEventId == null) {
			return null;
		}
		DbSelectPaginator<CommunityEventUser> p = 
				new DbSelectPaginator<CommunityEventUser>(this);
		p.addCriterion( Restrictions.eq("id.communityEventId", communityEventId) );
		p.addAssociation( Association.join("user", JoinType.INNER_JOIN) );
		p.setLimit(limit);
		p.setPage(page);
		return p.paginate();
	}
	
	public void create(Integer communityEventId, Integer userId) {
		CommunityEventUser ceu = new CommunityEventUser();
		CommunityEventUserId ceuId = new CommunityEventUserId();
		ceuId.setCommunityEventId(communityEventId);
		ceuId.setUserId(userId);
		ceu.setId(ceuId);
		persist(ceu);
	}
	
	
}
