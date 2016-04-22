package com.galaxii.common.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.dao.CommunityEventDao;
import com.galaxii.common.dao.CommunityEventUserDao;
import com.galaxii.common.dto.EventCalendar;
import com.galaxii.common.entity.CommunityEvent;
import com.galaxii.common.entity.Status;
import com.galaxii.common.entity.User;
import com.galaxii.common.hibernate.Association;
import com.galaxii.common.util.DbSelectPaginator;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.form.CommunityEventForm;

@Service
public class CommunityEventService {
	
	@Resource
	private CommunityEventDao communityEventDao;
	@Resource
	private CommunityEventUserDao communityEventUserDao;
	
	@Transactional
	public SimplePager<CommunityEvent> paginateByMonth(Integer communityId, int limit, EventCalendar ec) {
		DbSelectPaginator<CommunityEvent> p = 
				new DbSelectPaginator<CommunityEvent>(communityEventDao);
		p.setLimit(limit);
		p.addAssociation( Association.join("user", JoinType.INNER_JOIN) );
		p.addOrder(Order.desc("startAt"));
		p.addCriterion(Restrictions.eq("communityId", communityId));
		p.addCriterion( Restrictions.ge("startAt", ec.getMonthFirstDay()) );
		p.addCriterion( Restrictions.lt("startAt", ec.getNextMonth()) );
		return p.paginate();
	}
	
	@Transactional
	public CommunityEvent create(
			Integer communityId, CommunityEventForm form, User user) 
			throws Exception {
		
		// create 
		CommunityEvent event = new CommunityEvent();
		event.setTitle(form.getTitle());
		event.setDescription(form.getDescription());
		event.setUserId(user.getId());
		event.setCommunityId(communityId);
		event.setStartAt(form.getStartAt());
		event.setEndAt(form.getEndAt());
		event.setPlace(form.getPlace());
		event.setLat(form.getLat());
		event.setLon(form.getLon());
		event.setLikeCnt(1); // create user
		event.setCommentCnt(0);
		event.setJoinUsersCnt(0);
		event.setStatus(Status.LIVE);
		communityEventDao.save(event);
		
        // join
        joinUser(event, user.getId());
        
		return event;
	}
	
	@Transactional
	public CommunityEvent update(
			CommunityEvent event, CommunityEventForm form, User user) 
			throws Exception {
		event.setDescription(form.getDescription());
		event.setStartAt(form.getStartAt());
		event.setEndAt(form.getEndAt());
		event.setPlace(form.getPlace());
		event.setLat(form.getLat());
		event.setLon(form.getLon());
		communityEventDao.update(event);
		return event;
	}
	
	public void reflectForm(
			CommunityEventForm communityEventForm, CommunityEvent communityEvent) throws IllegalAccessException, InvocationTargetException {
		Date defaultValue = null;
		ConvertUtils.register(new DateConverter(defaultValue), Date.class);
		BeanUtils.copyProperties(communityEventForm, communityEvent);
	}
	
	@Transactional
	public boolean isMember(CommunityEvent communityEvent, Integer userId) {
        return communityEventUserDao.isExistCommunityEventUser(
        		communityEvent.getId(), userId);
	}
	
	@Transactional
	public void joinUser(CommunityEvent communityEvent, Integer userId) {
		communityEventUserDao.create(communityEvent.getId(), userId);
		communityEventDao.getSession().createQuery(
			"UPDATE CommunityEvent SET joinUsersCnt = joinUsersCnt + 1 WHERE id = :communityEventId" )
			.setInteger( "communityEventId", communityEvent.getId() )
			.executeUpdate();
	}
	
	@Transactional
	public void unJoinUser(CommunityEvent communityEvent, Integer userId) {
		communityEventUserDao.deleteById(communityEvent.getId(), userId);
		communityEventDao.getSession().createQuery(
			"UPDATE CommunityEvent SET joinUsersCnt = joinUsersCnt - 1 WHERE id = :communityEventId" )
			.setInteger( "communityEventId", communityEvent.getId() )
			.executeUpdate();
	}
}
