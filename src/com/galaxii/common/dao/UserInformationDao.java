package com.galaxii.common.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.UserInformation;
import com.galaxii.common.entity.UserInformationContentsCategory;
import com.galaxii.common.entity.UserInformationStatus;

@Transactional
@Repository
public class UserInformationDao extends AbstractDao<UserInformation> {

	public UserInformation insert(
			Integer fromUserId, Integer toUserId,
			UserInformationContentsCategory contentsCategory,
			Integer contentsId, String name) {
		if(fromUserId.equals(toUserId)) {
			return null;
		}
		UserInformation row = new UserInformation();
		row.setFromUserId(fromUserId);
		row.setToUserId(toUserId);
		row.setUserInformationContentsCategoryId(contentsCategory);
		row.setUserInformationContentsId(contentsId);
		row.setName(name);
		row.setStatus(UserInformationStatus.READY);
		save(row);
		return row;
	}
	
	public void delete(
			Integer fromUserId, Integer toUserId,
			UserInformationContentsCategory contentsCategory,
			Integer contentsId) {
		UserInformation row = find(fromUserId, toUserId, contentsCategory, contentsId);
		if(row == null) {
			return;
		}
		row.setStatus(UserInformationStatus.DELETED);
		update(row);
	}
	
	public UserInformation find(
			Integer fromUserId, Integer toUserId,
			UserInformationContentsCategory contentsCategory,
			Integer contentsId) {
		return (UserInformation)getSession().createQuery(
				"FROM UserInformation ui WHERE" +
				" ui.fromUserId = :fromUserId AND" +
				" ui.toUserId = :toUserId AND" +
				" ui.userInformationContentsCategoryId = :userInformationContentsCategoryId AND" +
				" ui.userInformationContentsId = :userInformationContentsId")
				.setInteger("fromUserId", fromUserId)
				.setInteger("toUserId", toUserId)
				.setParameter("userInformationContentsCategoryId", contentsCategory)
				.setInteger("userInformationContentsId", contentsId).uniqueResult();
	}
}
