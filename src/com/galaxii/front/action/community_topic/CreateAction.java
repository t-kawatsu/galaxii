package com.galaxii.front.action.community_topic;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityTopic;
import com.galaxii.common.service.CommunityActivityService;
import com.galaxii.common.service.CommunityTopicService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.CommunityTopicForm;

public class CreateAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private CommunityTopicForm communityTopicForm;
	@Resource
	private CommunityTopicService communityTopicService;
	@Resource
	private CommunityActivityService communityActivityService;
	@Resource
	private CommunityUserDao communityUserDao;
	@Resource
	private CommunityDao communityDao;

	private Integer id;
	private Integer communityId;
	private Community community;

	@Action(value="community-topic/create-ajax",
			results={
			@Result(name="input", location="community-topic/create-ajax.ftl"),
            @Result(name="success", location="community-topic/create-ajax-complete.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!communityTopicForm.validate(this)) {
            return INPUT;
        }
        if(!communityUserDao.isExistCommunityUser(
        		communityId, getCurrentUser().getId())) {
        	return REQUIRE_COMMUNITY_MEMBER;
        }
        // create
        CommunityTopic communityTopic = communityTopicService.create(
        		communityId, communityTopicForm, getCurrentUser());
        // activity
        communityActivityService
        	.insertCommunityTopicActivity(communityTopic);
		return SUCCESS;
    }
	
	@Action(value="community-topic/create",
		results={
			@Result(name="input", location="community-topic/create.ftl"),
            @Result(name="success", location="community-topic/read/${id}", type="redirect")
		}
	)
    public String createAction() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
		
		community = communityDao.findById(communityId);
		
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!communityTopicForm.validate(this)) {
            return INPUT;
        }
        if(!communityUserDao.isExistCommunityUser(
        		communityId, getCurrentUser().getId())) {
        	return INPUT;
        }
        // create
        CommunityTopic communityTopic = communityTopicService.create(
        		communityId, communityTopicForm, getCurrentUser());
        this.id = communityTopic.getId();
        // activity
        communityActivityService
        	.insertCommunityTopicActivity(communityTopic);
        // user info
        userInformationService.insertCommunityTopic(communityId, getCurrentUser());
		return SUCCESS;
    }
	
	public Integer getId() {
		return id;
	}

	public Integer getCommunityId() {
		return communityId;
	}
	
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
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
}