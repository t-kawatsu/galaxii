package com.galaxii.front.action.community_recommend;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityActivityDao;
import com.galaxii.common.dao.CommunityRecommendDao;
import com.galaxii.common.entity.CommunityContentsCategory;
import com.galaxii.common.entity.CommunityRecommend;
import com.galaxii.front.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityRecommendDao communityRecommendDao;
	@Resource
	private CommunityActivityDao communityAvtivityDao;
	
	private Integer id;
	private CommunityRecommend communityRecommend;

	@Action(value="community-recommend/delete-ajax/{id}",
		results={
			 @Result(name="success", 
					 location="community-recommend/delete-complete.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
		communityRecommend = communityRecommendDao.findById(id);
        if(communityRecommend == null 
        	|| !communityRecommend.getUserId().equals(getCurrentUser().getId())) {
            return ERROR;
        }
        communityRecommendDao.delete(communityRecommend);
        
        communityAvtivityDao.deleteActivity(
        		CommunityContentsCategory.RECOMMEND, id);
        // user info
        userInformationService.deleteCommunityRecommend(
        		communityRecommend.getCommunityId(), getCurrentUser());
		return SUCCESS;
    }

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public CommunityRecommend getCommunityRecommend() {
		return communityRecommend;
	}
}