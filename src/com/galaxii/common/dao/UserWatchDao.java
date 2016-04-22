package com.galaxii.common.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.UserWatch;
import com.galaxii.common.entity.UserWatchId;

@Repository
@Transactional
public class UserWatchDao extends AbstractDao<UserWatch> {

	public void create(Integer fromUserId, Integer toUserId) {
		UserWatch uw = new UserWatch();
		UserWatchId uwId = new UserWatchId();
		uwId.setFromUserId(fromUserId);
		uwId.setToUserId(toUserId);
		uw.setId(uwId);
		persist(uw);
	}
	
}
