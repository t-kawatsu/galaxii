package com.galaxii.common.service;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.dao.CommunityCommentDao;
import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityEventCommentDao;
import com.galaxii.common.dao.CommunityEventDao;
import com.galaxii.common.dao.CommunityImageCommentDao;
import com.galaxii.common.dao.CommunityImageDao;
import com.galaxii.common.dao.CommunityRecommendDao;
import com.galaxii.common.dao.CommunityTopicCommentDao;
import com.galaxii.common.dao.CommunityTopicDao;
import com.galaxii.common.dao.UserViolationDao;
import com.galaxii.common.entity.UserViolation;
import com.galaxii.common.entity.UserViolationCategory;
import com.galaxii.common.entity.UserViolationableEntity;
import com.galaxii.front.form.UserViolationForm;

@Service
@Transactional
public class UserViolationService {
	
	@Resource
	private UserViolationDao userViolationDao;
	@Resource
	private CommunityDao communityDao;
	@Resource
	private CommunityCommentDao communityCommentDao;
	@Resource
	private CommunityImageDao communityImageDao;
	@Resource
	private CommunityImageCommentDao communityImageCommentDao;
	@Resource
	private CommunityTopicDao communityTopicDao;
	@Resource
	private CommunityTopicCommentDao communityTopicCommentDao;
	@Resource
	private CommunityEventDao communityEventDao;
	@Resource
	private CommunityEventCommentDao communityEventCommentDao;
	@Resource
	private CommunityRecommendDao communityRecommendDao;

	public UserViolation create(UserViolationForm userViolationForm, Integer userId) 
			throws IllegalAccessException, InvocationTargetException {
		UserViolation userViolation = new UserViolation();
		BeanUtils.copyProperties(userViolation, userViolationForm);
		UserViolationableEntity violationEntity = factoryViolationEntity(
				userViolation.getCategory(), userViolation.getCategoryDataId());
		if(violationEntity == null) {
			return null;
		}
		userViolation.setUserId(violationEntity.getUserId());
		userViolation.setReportUserId(userId);
		userViolationDao.save(userViolation);
		return userViolation;
	}
	
	public UserViolationableEntity factoryViolationEntity(
			UserViolationCategory userViolationCategory, Integer id) {
		switch(userViolationCategory) {
		case COMMUNITY:
			return (UserViolationableEntity)communityDao.findById(id);
		case COMMUNITY_COMMENT:
			return (UserViolationableEntity)communityCommentDao.findById(id);
		case COMMUNITY_IMAGE:
			return (UserViolationableEntity)communityImageDao.findById(id);	
		case COMMUNITY_IMAGE_COMMENT:
			return (UserViolationableEntity)communityImageCommentDao.findById(id);	
		case COMMUNITY_EVENT:
			return (UserViolationableEntity)communityEventDao.findById(id);	
		case COMMUNITY_EVENT_COMMENT:
			return (UserViolationableEntity)communityEventCommentDao.findById(id);	
		case COMMUNITY_TOPIC:
			return (UserViolationableEntity)communityTopicDao.findById(id);	
		case COMMUNITY_TOPIC_COMMENT:
			return (UserViolationableEntity)communityTopicCommentDao.findById(id);
		case COMMUNITY_RECOMMEND:
			return (UserViolationableEntity)communityRecommendDao.findById(id);
		default:
		}
		return null;
	}
}
