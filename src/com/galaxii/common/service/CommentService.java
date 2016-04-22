package com.galaxii.common.service;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.dao.AbstractDao;
import com.galaxii.common.dao.CommentDao;
import com.galaxii.common.dao.CommentableEntityDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.User;
import com.galaxii.common.hibernate.Association;
import com.galaxii.common.util.DbSelectMorePaginator;
import com.galaxii.common.util.SessionUtil;

@Service
@Transactional
public class CommentService {
	
	public static <T extends CommentEntity> DbSelectMorePaginator<CommentEntity> factorySessionPaginator(
			SessionUtil sessUtil, CommentDao<T> dao, Integer id, int limit) {
		@SuppressWarnings("unchecked")
		DbSelectMorePaginator<CommentEntity> p = 
				new DbSelectMorePaginator<CommentEntity>(sessUtil, (AbstractDao<CommentEntity>) dao, id);
		p.setLimit(limit);
		p.addAssociation( Association.join("user", JoinType.INNER_JOIN) );
		p.addCriterion( Restrictions.eq(dao.getBaseEntityIdRefer(), id) );
		p.addOrder(Order.desc("id"));
		
		//User user = (User)sessUtil.getCurrentUser();
		//if(user != null) {
		 // p.addAssociation( Association.join("commentLikes", JoinType.LEFT_OUTER_JOIN, "cl", Restrictions.eq("cl.id.userId", user.getId())));
		 // p.addCriterion( Restrictions.eq("cl.id.userId", user.getId()) );
		//}
		return p;
	}
	
	public <T extends CommentEntity> CommentEntity comment(
			CommentableEntityDao commentableDao, 
			CommentDao<T> commentDao, 
			Integer commentableEntityId, 
			String description,
			User user) {
        CommentEntity comment = commentDao.comment(
        		commentableEntityId, description, user);
        commentableDao.commentCountUp(commentableEntityId);
        return comment;
	}
}
