package com.galaxii.common.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.CommunityActivity;
import com.galaxii.common.entity.CommunityContentsCategory;

@Repository
@Transactional
public class CommunityActivityDao extends AbstractDao<CommunityActivity> {
	
	public void insertActivity(
			Integer communityId,
			Integer userId,
			CommunityContentsCategory communityContentsCategory,
			Integer id,
			String title) {
		CommunityActivity communityActivity = new CommunityActivity();
		communityActivity.setCommunityId(communityId);
		communityActivity.setCommunityContentsCategoryId(communityContentsCategory);
		communityActivity.setName(title);
		communityActivity.setUserId(userId);
		communityActivity.setCommunityContentsId(id);
		getSession().save(communityActivity);
	}

	public void deleteActivity(CommunityContentsCategory communityContentsCategory, Integer id) {
		CommunityActivity entity = (CommunityActivity)getSession().createQuery(
				"FROM CommunityActivity ca WHERE " +
				"ca.communityContentsId = :id AND " +
				"ca.communityContentsCategoryId = :categoryId")
				.setInteger("id", id)
				.setParameter("categoryId", communityContentsCategory).uniqueResult();
		if(entity == null) {
			return;
		}
		this.delete(entity);
	}
}
