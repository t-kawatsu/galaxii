package com.galaxii.front.action.community_image;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.entity.CommunityImage;
import com.galaxii.common.service.CommunityActivityService;
import com.galaxii.common.service.CommunityImageService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.CommunityImageForm;

public class MovieCreateAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityImageService communityImageService;
	@Resource
	private CommunityActivityService communityActivityService;
	@Resource
	private CommunityUserDao communityUserDao;
	@Resource
	private CommunityImageForm communityImageForm;
	
	private Integer communityId;

	@Action(value="community-image/movie-create-ajax",
			results={
			@Result(name="input", location="community-image/movie-create.ftl"),
            @Result(name="success", location="community-image/movie-create-complete.ftl")
		}
	)
    public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
        if(!communityUserDao.isExistCommunityUser(
        		communityId, getCurrentUser().getId())) {
        	return REQUIRE_COMMUNITY_MEMBER;
        }
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!communityImageForm.validate(this)) {
            return INPUT;
        }
        
        CommunityImage communityImage = communityImageService.create(
        		communityId, communityImageForm, getCurrentUser());
        
        communityActivityService
        	.insertCommunityImageActivity(communityImage);
		return SUCCESS;
    }
	
	public Integer getCommunityId() {
		return communityId;
	}
	
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public CommunityImageForm getCommunityImageForm() {
		return communityImageForm;
	}

	public void setCommunityImageForm(CommunityImageForm communityImageForm) {
		this.communityImageForm = communityImageForm;
	}
	
}