package com.galaxii.front.action.community;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.Community;
import com.galaxii.common.service.CommunityImageService;
import com.galaxii.common.service.CommunityService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.CommunityForm;

public class UpdateAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private CommunityImageService communityImageService;
	@Resource
	private CommunityService communityService;
	@Resource
	private CommunityForm communityForm;
	
	private Integer id;
	private Community community;
	
	@Action(value="community/update/{id}",
		results={
			@Result(name="input", location="community/update.ftl"),
            @Result(name="success", location="community/read/${id}", type="redirect")
		}
	)
	@Override
    public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
		community = communityService.findById(id);
		if(community == null
				|| !community.getUserId().equals(getCurrentUser().getId())) {
				return ERROR;
			}
        if(!"POST".equals(request.getMethod())) {
        	communityImageService.clearTmpImageDir(getCurrentUser().getId());
        	communityService.reflectForm(communityForm, community);
            return INPUT;
        }
        communityForm.setTitle(community.getTitle());
        if(!communityForm.validate(this)) {
            return INPUT;
        }
        // update 
        community = communityService.update(
        		community, communityForm, getCurrentUser());
		return SUCCESS;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setCommunityForm(CommunityForm communityForm) {
		this.communityForm = communityForm;
	}
	
	public CommunityForm getCommunityForm() {
		return communityForm;
	}
	
	public Community getCommunity() {
		return community;
	}
}