package com.galaxii.front.action.community_topic;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityActivityDao;
import com.galaxii.common.dao.CommunityTopicDao;
import com.galaxii.common.entity.CommunityContentsCategory;
import com.galaxii.common.entity.CommunityTopic;
import com.galaxii.front.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityTopicDao communityTopicDao;
	@Resource
	private CommunityActivityDao communityAvtivityDao;
	
	private Integer id;
	private CommunityTopic communityTopic;

	@Action(value="community-topic/delete-ajax/{id}",
		results={
            @Result(name="success", location="community-topic/delete-complete.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
		communityTopic = communityTopicDao.findById(id);
        if(communityTopic == null 
        		|| !communityTopic.getUserId().equals(getCurrentUser().getId())) {
            return ERROR;
        }
        communityTopicDao.delete(communityTopic);
        
        communityAvtivityDao.deleteActivity(
        		CommunityContentsCategory.REVIEW, id);
        // user info
        userInformationService.deleteCommunityTopic(
        		communityTopic.getCommunityId(), getCurrentUser());
		return SUCCESS;
    }

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public CommunityTopic getCommunityTopic() {
		return communityTopic;
	}
}