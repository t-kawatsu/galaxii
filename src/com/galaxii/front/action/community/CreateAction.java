package com.galaxii.front.action.community;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.Community;
import com.galaxii.common.service.CommunityActivityService;
import com.galaxii.common.service.CommunityService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.CommunityForm;

public class CreateAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private CommunityService communityService;
	@Resource
	private CommunityActivityService communityActivityService;
	@Resource
	private CommunityForm communityForm;
	
	private Integer communityId;
	
	@Action(value="community/create",
		results={
			@Result(name="input", location="community/create.ftl"),
            @Result(name="success", location="community/read/${communityId}", type="redirect")
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
        if(!communityForm.validate(this)) {
            return INPUT;
        }
        // create 
        Community community = communityService.create(communityForm, getCurrentUser());
		communityActivityService.insertCommunityActivity(community);
		communityId = community.getId();
		return SUCCESS;
    }
	
	public Integer getCommunityId() {
		return communityId;
	}
	
	public void setCommunityForm(CommunityForm communityForm) {
		this.communityForm = communityForm;
	}
	
	public CommunityForm getCommunityForm() {
		return communityForm;
	}
}