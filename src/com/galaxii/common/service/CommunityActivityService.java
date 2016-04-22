package com.galaxii.common.service;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.dao.CommunityActivityDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityActivity;
import com.galaxii.common.entity.CommunityContentsCategory;
import com.galaxii.common.entity.CommunityEvent;
import com.galaxii.common.entity.CommunityImage;
import com.galaxii.common.entity.CommunityRecommend;
import com.galaxii.common.entity.CommunityTopic;
import com.galaxii.common.hibernate.Association;
import com.galaxii.common.util.DbSelectMorePaginator;
import com.galaxii.common.util.SessionUtil;

@Service
@Transactional
public class CommunityActivityService {
	
	@Resource
	CommunityActivityDao communityActivityDao;
	
	public DbSelectMorePaginator<CommunityActivity> factorySessionPaginator(
			SessionUtil sessUtil, Integer communityId, int limit) {
		DbSelectMorePaginator<CommunityActivity> p = new DbSelectMorePaginator<CommunityActivity>(
						sessUtil, communityActivityDao, communityId);
		p.setLimit(limit);
		p.addAssociation( Association.join("user", JoinType.INNER_JOIN) );
		p.addCriterion(Restrictions.eq("communityId", communityId));
		p.addOrder(Order.desc("id"));
		return p;
	}
	
	public DbSelectMorePaginator<CommunityActivity> factoryGlobalListSessionPaginator(
			SessionUtil sessUtil, int limit) {
		DbSelectMorePaginator<CommunityActivity> p = new DbSelectMorePaginator<CommunityActivity>(
						sessUtil, communityActivityDao, 0);
		p.setLimit(limit);
		p.addAssociation( Association.join("user", JoinType.INNER_JOIN) );
		p.addAssociation( Association.join("community", JoinType.INNER_JOIN) );
		p.addOrder(Order.desc("id"));
		return p;
	}
	
	public DbSelectMorePaginator<CommunityActivity> factoryUserListSessionPaginator(
			SessionUtil sessUtil, Integer userId, int limit) {
		DbSelectMorePaginator<CommunityActivity> p = new DbSelectMorePaginator<CommunityActivity>(
						sessUtil, communityActivityDao, userId);
		p.setLimit(limit);
		p.addAssociation( Association.join("community", JoinType.INNER_JOIN) );
		p.addCriterion(Restrictions.eq("userId", userId));
		p.addOrder(Order.desc("id"));
		return p;
	}
	
	public DbSelectMorePaginator<CommunityActivity> factoryWatchUserActivityListSessionPaginator(
			SessionUtil sessUtil, Integer userId, int limit) {
		DbSelectMorePaginator<CommunityActivity> p = new DbSelectMorePaginator<CommunityActivity>(
						sessUtil, communityActivityDao, userId);
		p.setLimit(limit);
		p.addAssociation( Association.join("toUserWatches", JoinType.INNER_JOIN, "toUserWatch") );
		p.addAssociation( Association.join("user", JoinType.INNER_JOIN) );
		p.addAssociation( Association.join("community", JoinType.INNER_JOIN) );
		p.addCriterion(Restrictions.eq("toUserWatch.id.fromUserId", userId));
		p.addOrder(Order.desc("id"));
		return p;
	}
	/*
	public DbSelectMorePaginator<CommunityActivity> factoryWatchedUserActivityListSessionPaginator(
			SessionUtil sessUtil, Integer userId, int limit) {
		DbSelectMorePaginator<CommunityActivity> p = new DbSelectMorePaginator<CommunityActivity>(
						sessUtil, communityActivityDao, userId);
		p.setLimit(limit);
		p.addAssociation( Association.join("fromUserWatches", JoinType.INNER_JOIN, "fromUserWatch") );
		p.addAssociation( Association.join("user", JoinType.INNER_JOIN) );
		p.addAssociation( Association.join("community", JoinType.INNER_JOIN) );
		p.addCriterion(Restrictions.eq("fromUserWatch.id.toUserId", userId));
		p.addOrder(Order.desc("id"));
		return p;
	}
	*/
	
	public void insertCommentActivity(CommentEntity comment) {
		communityActivityDao.insertActivity(
				comment.getBaseId(),
				comment.getUserId(),
				CommunityContentsCategory.COMMENT,
				comment.getId(),
				comment.getDescription());
	}
	
	public void insertCommunityActivity(Community community) {
		communityActivityDao.insertActivity(
				community.getId(),
				community.getUserId(),
				CommunityContentsCategory.HOME,
				community.getId(),
				community.getTitle());
	}
	
	public void insertCommunityImageActivity(CommunityImage communityImage) {
		communityActivityDao.insertActivity(
				communityImage.getCommunityId(),
				communityImage.getUserId(),
				CommunityContentsCategory.IMAGE,
				communityImage.getId(),
				communityImage.getTitle());
	}
	
	public void insertCommunityTopicActivity(CommunityTopic topic) {
		communityActivityDao.insertActivity(
				topic.getCommunityId(),
				topic.getUserId(),
				CommunityContentsCategory.REVIEW,
				topic.getId(),
				topic.getTitle());
	}
	public void insertCommunityEventActivity(CommunityEvent event) {
		communityActivityDao.insertActivity(
				event.getCommunityId(),
				event.getUserId(),
				CommunityContentsCategory.EVENT,
				event.getId(),
				event.getTitle());
	}
	
	public void insertCommunityRecommendActivity(CommunityRecommend communityRecommend) {
		communityActivityDao.insertActivity(
				communityRecommend.getCommunityId(),
				communityRecommend.getUserId(),
				CommunityContentsCategory.RECOMMEND,
				communityRecommend.getId(),
				communityRecommend.getTitle());
	}
	
}
