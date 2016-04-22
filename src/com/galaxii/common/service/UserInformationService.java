package com.galaxii.common.service;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.UserInformationDao;
import com.galaxii.common.entity.CommentEntity;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityEvent;
import com.galaxii.common.entity.CommunityImage;
import com.galaxii.common.entity.CommunityRecommend;
import com.galaxii.common.entity.CommunityTopic;
import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserInformation;
import com.galaxii.common.entity.UserInformationContentsCategory;
import com.galaxii.common.entity.UserInformationStatus;
import com.galaxii.common.hibernate.Association;
import com.galaxii.common.util.DbSelectMorePaginator;
import com.galaxii.common.util.DbSelectPaginator;
import com.galaxii.common.util.SessionUtil;
import com.galaxii.common.util.SimplePager;

@Transactional
@Service
public class UserInformationService {

	@Resource
	private UserInformationDao userInformationDao;
	@Resource
	private CommunityDao communityDao;
	
	public DbSelectMorePaginator<UserInformation> factorySessionPaginator(
			SessionUtil sessUtil, Integer toUserId, int limit) {
		DbSelectMorePaginator<UserInformation> p = new DbSelectMorePaginator<UserInformation>(
						sessUtil, userInformationDao, toUserId);
		p.setLimit(limit);
		p.addAssociation( Association.join("fromUser", JoinType.INNER_JOIN) );
		p.addCriterion(Restrictions.eq("toUserId", toUserId));
		p.addCriterion(Restrictions.eq("status", UserInformationStatus.ACCEPT));
		p.addOrder(Order.desc("id"));
		return p;
	}
	
	public SimplePager<UserInformation> paginateReadyState(Integer limit, Integer page) {
		DbSelectPaginator<UserInformation> p =
				new DbSelectPaginator<UserInformation>(userInformationDao);
		p.addCriterion( Restrictions.eq("status", UserInformationStatus.READY));
		p.setLimit(limit);
		p.setPage(page);
		return p.paginate();
	}
	
	public void insertCommunityComment(CommentEntity comment, User user) {
		Community community = communityDao.findById(comment.getBaseId());
		userInformationDao.insert(
				user.getId(),
				community.getUserId(),
				UserInformationContentsCategory.COMMUNITY_COMMENT,
				community.getId(),
				community.getTitle());
	}
	
	public void deleteCommunityComment(CommentEntity comment, User user) {
		Community community = communityDao.findById(comment.getBaseId());
		userInformationDao.delete(
				user.getId(),
				community.getUserId(),
				UserInformationContentsCategory.COMMUNITY_COMMENT,
				community.getId());
	}
	
	public void insertCommunityCommentLike(CommentEntity comment, User user) {
		userInformationDao.insert(
				user.getId(),
				comment.getUserId(),
				UserInformationContentsCategory.COMMUNITY_COMMENT_LIKE,
				comment.getId(),
				comment.getDescription());
	}
	
	public void deleteCommunityCommentLike(CommentEntity comment, User user) {
		userInformationDao.delete(
				user.getId(),
				comment.getUserId(),
				UserInformationContentsCategory.COMMUNITY_COMMENT_LIKE,
				comment.getId());
	}
	
	public void insertCommunityEvent(Integer communityId, User user) {
		Community community = communityDao.findById(communityId);
		userInformationDao.insert(
				user.getId(),
				community.getUserId(),
				UserInformationContentsCategory.COMMUNITY_EVENT,
				community.getId(),
				community.getTitle());
	}
	
	public void deleteCommunityEvent(Integer communityId, User user) {
		Community community = communityDao.findById(communityId);
		userInformationDao.delete(
				user.getId(),
				community.getUserId(),
				UserInformationContentsCategory.COMMUNITY_EVENT,
				community.getId());
	}
	
	public void insertCommunityEventComment(CommunityEvent event, User user) {
		userInformationDao.insert(
				user.getId(),
				event.getUserId(),
				UserInformationContentsCategory.COMMUNITY_EVENT_COMMENT,
				event.getId(),
				event.getTitle());
	}
	
	public void deleteCommunityEventComment(CommunityEvent event, User user) {
		userInformationDao.delete(
				user.getId(),
				event.getUserId(),
				UserInformationContentsCategory.COMMUNITY_EVENT_COMMENT,
				event.getId());
	}
	
	public void insertCommunityEventCommentLike(CommentEntity comment, User user) {
		userInformationDao.insert(
				user.getId(),
				comment.getUserId(),
				UserInformationContentsCategory.COMMUNITY_EVENT_COMMENT_LIKE,
				comment.getId(),
				comment.getDescription());
	}
	
	public void deleteCommunityEventCommentLike(CommentEntity comment, User user) {
		userInformationDao.delete(
				user.getId(),
				comment.getUserId(),
				UserInformationContentsCategory.COMMUNITY_EVENT_COMMENT_LIKE,
				comment.getId());
	}
	
	public void insertCommunityEventLike(CommunityEvent event, User user) {
		userInformationDao.insert(
				user.getId(),
				event.getUserId(),
				UserInformationContentsCategory.COMMUNITY_EVENT_LIKE,
				event.getId(),
				event.getTitle());
	}
	
	public void deleteCommunityEventLike(CommunityEvent event, User user) {
		userInformationDao.delete(
				user.getId(),
				event.getUserId(),
				UserInformationContentsCategory.COMMUNITY_EVENT_LIKE,
				event.getId());
	}
	
	public void insertCommunityEventUser(CommunityEvent event, User user) {
		userInformationDao.insert(
				user.getId(),
				event.getUserId(),
				UserInformationContentsCategory.COMMUNITY_EVENT_USER,
				event.getId(),
				event.getTitle());
	}
	
	public void deleteCommunityEventUser(CommunityEvent event, User user) {
		userInformationDao.delete(
				user.getId(),
				event.getUserId(),
				UserInformationContentsCategory.COMMUNITY_EVENT_USER,
				event.getId());
	}
	
	public void insertCommunityImage(Integer communityId, User user) {
		Community community = communityDao.findById(communityId);
		userInformationDao.insert(
				user.getId(),
				community.getUserId(),
				UserInformationContentsCategory.COMMUNITY_IMAGE,
				community.getId(),
				community.getTitle());
	}
	
	public void deleteCommunityImage(Integer communityId, User user) {
		Community community = communityDao.findById(communityId);
		userInformationDao.delete(
				user.getId(),
				community.getUserId(),
				UserInformationContentsCategory.COMMUNITY_IMAGE,
				community.getId());
	}
	
	public void insertCommunityImageComment(CommunityImage image, User user) {
		userInformationDao.insert(
				user.getId(),
				image.getUserId(),
				UserInformationContentsCategory.COMMUNITY_IMAGE_COMMENT,
				image.getId(),
				image.getTitle());
	}
	
	public void deleteCommunityImageComment(CommunityImage image, User user) {
		userInformationDao.delete(
				user.getId(),
				image.getUserId(),
				UserInformationContentsCategory.COMMUNITY_IMAGE_COMMENT,
				image.getId());
	}
	
	public void insertCommunityImageCommentLike(CommentEntity comment, User user) {
		userInformationDao.insert(
				user.getId(),
				comment.getUserId(),
				UserInformationContentsCategory.COMMUNITY_IMAGE_COMMENT_LIKE,
				comment.getId(),
				comment.getDescription());
	}
	
	public void deleteCommunityImageCommentLike(CommentEntity comment, User user) {
		userInformationDao.delete(
				user.getId(),
				comment.getUserId(),
				UserInformationContentsCategory.COMMUNITY_IMAGE_COMMENT_LIKE,
				comment.getId());
	}
	
	public void insertCommunityImageLike(CommunityImage image, User user) {
		userInformationDao.insert(
				user.getId(),
				image.getUserId(),
				UserInformationContentsCategory.COMMUNITY_IMAGE_LIKE,
				image.getId(),
				image.getTitle());
	}
	
	public void deleteCommunityImageLike(CommunityImage image, User user) {
		userInformationDao.delete(
				user.getId(),
				image.getUserId(),
				UserInformationContentsCategory.COMMUNITY_IMAGE_LIKE,
				image.getId());
	}
	
	public void insertCommunityRecommend(Integer communityId, User user) {
		Community community = communityDao.findById(communityId);
		userInformationDao.insert(
				user.getId(),
				community.getUserId(),
				UserInformationContentsCategory.COMMUNITY_RECOMMEND,
				community.getId(),
				community.getTitle());
	}
	
	public void deleteCommunityRecommend(Integer communityId, User user) {
		Community community = communityDao.findById(communityId);
		userInformationDao.delete(
				user.getId(),
				community.getUserId(),
				UserInformationContentsCategory.COMMUNITY_RECOMMEND,
				community.getId());
	}
	
	public void insertCommunityRecommendComment(CommunityRecommend recommend, User user) {
		userInformationDao.insert(
				user.getId(),
				recommend.getUserId(),
				UserInformationContentsCategory.COMMUNITY_RECOMMEND_COMMENT,
				recommend.getId(),
				recommend.getTitle());
	}
	
	public void deleteCommunityRecommendComment(CommunityRecommend recommend, User user) {
		userInformationDao.delete(
				user.getId(),
				recommend.getUserId(),
				UserInformationContentsCategory.COMMUNITY_RECOMMEND_COMMENT,
				recommend.getId());
	}
	
	public void insertCommunityRecommendCommentLike(CommentEntity comment, User user) {
		userInformationDao.insert(
				user.getId(),
				comment.getUserId(),
				UserInformationContentsCategory.COMMUNITY_RECOMMEND_COMMENT_LIKE,
				comment.getId(),
				comment.getDescription());
	}
	
	public void deleteCommunityRecommendCommentLike(CommentEntity comment, User user) {
		userInformationDao.delete(
				user.getId(),
				comment.getUserId(),
				UserInformationContentsCategory.COMMUNITY_RECOMMEND_COMMENT_LIKE,
				comment.getId());
	}
	
	public void insertCommunityRecommendLike(CommunityRecommend recommend, User user) {
		userInformationDao.insert(
				user.getId(),
				recommend.getUserId(),
				UserInformationContentsCategory.COMMUNITY_RECOMMEND_LIKE,
				recommend.getId(),
				recommend.getTitle());
	}
	
	public void deleteCommunityRecommendLike(CommunityRecommend recommend, User user) {
		userInformationDao.delete(
				user.getId(),
				recommend.getUserId(),
				UserInformationContentsCategory.COMMUNITY_RECOMMEND_LIKE,
				recommend.getId());
	}
	
	public void insertCommunityTopic(Integer communityId, User user) {
		Community community = communityDao.findById(communityId);
		userInformationDao.insert(
				user.getId(),
				community.getUserId(),
				UserInformationContentsCategory.COMMUNITY_TOPIC,
				community.getId(),
				community.getTitle());
	}
	
	public void deleteCommunityTopic(Integer communityId, User user) {
		Community community = communityDao.findById(communityId);
		userInformationDao.delete(
				user.getId(),
				community.getUserId(),
				UserInformationContentsCategory.COMMUNITY_TOPIC,
				community.getId());
	}
	
	public void insertCommunityTopicComment(CommunityTopic topic, User user) {
		userInformationDao.insert(
				user.getId(),
				topic.getUserId(),
				UserInformationContentsCategory.COMMUNITY_TOPIC_COMMENT,
				topic.getId(),
				topic.getTitle());
	}
	
	public void deleteCommunityTopicComment(CommunityTopic topic, User user) {
		userInformationDao.delete(
				user.getId(),
				topic.getUserId(),
				UserInformationContentsCategory.COMMUNITY_TOPIC_COMMENT,
				topic.getId());
	}
	
	public void insertCommunityTopicCommentLike(CommentEntity comment, User user) {
		userInformationDao.insert(
				user.getId(),
				comment.getUserId(),
				UserInformationContentsCategory.COMMUNITY_TOPIC_COMMENT_LIKE,
				comment.getId(),
				comment.getDescription());
	}
	
	public void deleteCommunityTopicCommentLike(CommentEntity comment, User user) {
		userInformationDao.delete(
				user.getId(),
				comment.getUserId(),
				UserInformationContentsCategory.COMMUNITY_TOPIC_COMMENT_LIKE,
				comment.getId());
	}
	
	public void insertCommunityTopicLike(CommunityTopic topic, User user) {
		userInformationDao.insert(
				user.getId(),
				topic.getUserId(),
				UserInformationContentsCategory.COMMUNITY_TOPIC_LIKE,
				topic.getId(),
				topic.getTitle());
	}
	
	public void deleteCommunityTopicLike(CommunityTopic topic, User user) {
		userInformationDao.delete(
				user.getId(),
				topic.getUserId(),
				UserInformationContentsCategory.COMMUNITY_TOPIC_LIKE,
				topic.getId());
	}
	
	public void insertCommunityMember(Community community, User user) {
		userInformationDao.insert(
				user.getId(),
				community.getUserId(),
				UserInformationContentsCategory.COMMUNITY_JOIN,
				community.getId(),
				community.getTitle());
	}
	
	public void deleteCommunityMember(Community community, User user) {
		userInformationDao.delete(
				user.getId(),
				community.getUserId(),
				UserInformationContentsCategory.COMMUNITY_JOIN,
				community.getId());
	}
	
	public void insertWatch(User toUser, User user) {
		userInformationDao.insert(
				user.getId(),
				toUser.getId(),
				UserInformationContentsCategory.WATCH,
				toUser.getId(),
				toUser.getNickname());
	}
	
	public void deleteWatch(User toUser, User user) {
		userInformationDao.delete(
				user.getId(),
				toUser.getId(),
				UserInformationContentsCategory.WATCH,
				toUser.getId());
	}
	
	@SuppressWarnings("unused")
	private boolean verifyInsertable() {
		return false;
	}
}
