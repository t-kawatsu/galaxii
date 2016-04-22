package com.galaxii.front.action.community_topic;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityTopicDao;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityTopic;
import com.galaxii.common.service.CommunityTopicService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.CommunityTopicForm;

public class UpdateAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private CommunityTopicForm communityTopicForm;
	@Resource
	private CommunityTopicService communityTopicService;
	@Resource
	private CommunityDao communityDao;
	@Resource
	private CommunityTopicDao communityTopicDao;

	private Integer id;
	private Community community;
	private CommunityTopic communityTopic;
	
	@Action(value="community-topic/update/{id}",
		results={
			@Result(name="input", location="community-topic/update.ftl"),
            @Result(name="success", location="community-topic/read/${id}", type="redirect")
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
		community = communityDao.findById(communityTopic.getCommunityId());
        if(!"POST".equals(request.getMethod())) {
        	communityTopicService.clearTmpImageDir(getCurrentUser().getId());
        	communityTopicService.reflectForm(
        			communityTopicForm, communityTopic);
            return INPUT;
        }   
        if(!communityTopicForm.validateUpdate(this)) {
            return INPUT;
        }
        // update
        communityTopic = communityTopicService.update(
        		communityTopic, communityTopicForm, getCurrentUser());
		return SUCCESS;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public CommunityTopicForm getCommunityTopicForm() {
		return communityTopicForm;
	}

	public void setCommunityTopicForm(CommunityTopicForm communityTopicForm) {
		this.communityTopicForm = communityTopicForm;
	}
	
	public Community getCommunity() {
		return community;
	}
	
	public CommunityTopic getCommunityTopic() {
		return communityTopic;
	}
}