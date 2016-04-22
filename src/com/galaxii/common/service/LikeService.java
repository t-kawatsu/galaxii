package com.galaxii.common.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.dao.AbstractDao;
import com.galaxii.common.dao.LikableEntityDao;
import com.galaxii.common.dao.LikeDao;
import com.galaxii.common.entity.LikeEntity;
import com.galaxii.common.hibernate.Association;
import com.galaxii.common.util.DbSelectMorePaginator;
import com.galaxii.common.util.SessionUtil;

@Transactional
@Service
public class LikeService {
	
	
	public <T extends LikeEntity> DbSelectMorePaginator<LikeEntity> factorySessionPaginator(
			SessionUtil sessUtil, LikeDao dao, Integer id, int limit) {
		@SuppressWarnings("unchecked")
		DbSelectMorePaginator<LikeEntity> p = 
				new DbSelectMorePaginator<LikeEntity>(sessUtil, (AbstractDao<LikeEntity>) dao, id);
		p.setLimit(limit);
		p.addAssociation( Association.join("user", JoinType.INNER_JOIN) );
		p.addCriterion( Restrictions.eq(dao.getBaseEntityIdRefer(), id) );
		p.addOrder(Order.desc("createdAt"));
		return p;
	}

	public Map<String, Object> like(
			LikableEntityDao likableDao, 
			LikeDao likeDao, 
			Integer likableEntityId, 
			Integer userId) {
        if(!likeDao.isLiked(likableEntityId, userId)) {
        	likeDao.create(likableEntityId, userId);
        	likableDao.likeCountUp(likableEntityId);
        }
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("count", likeDao.countByBaseId(likableEntityId));
        ret.put("liked", true);
        return ret;
	}
	
	public Map<String, Object> unlike(
			LikableEntityDao likableDao, 
			LikeDao likeDao, 
			Integer likableEntityId, 
			Integer userId) {
        if(likeDao.isLiked(likableEntityId, userId)) {
        	likeDao.deleteById(likableEntityId, userId);
        	likableDao.likeCountDown(likableEntityId);
        }
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("count", likeDao.countByBaseId(likableEntityId));
        ret.put("liked", false);
        return ret;
	}
}
