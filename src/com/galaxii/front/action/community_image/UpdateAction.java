package com.galaxii.front.action.community_image;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityImageDao;
import com.galaxii.common.entity.CommunityImage;
import com.galaxii.common.service.CommunityImageService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.CommunityImageForm;

public class UpdateAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityImageForm communityImageForm;
	@Resource
	private CommunityImageService communityImageService;
	@Resource
	private CommunityImageDao communityImageDao;

	private Integer id;
	private CommunityImage communityImage;
	
	@Action(value="community-image/update-ajax/{id}",
			results={
			@Result(name="input", location="community-image/update.ftl"),
            @Result(name="success", location="community-image/update-complete.ftl")
		}
	)
    public String createAjaxAction() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
		communityImage = communityImageDao.findById(id);
		if(communityImage == null 
				|| !communityImage.getUserId().equals(getCurrentUser().getId())) {
			return ERROR;
		}
        if(!"POST".equals(request.getMethod())) {
        	BeanUtils.copyProperties(communityImageForm, communityImage);
            return INPUT;
        }   
        if(!communityImageForm.validateUpdate(this)) {
            return INPUT;
        }
        // update
        communityImage = communityImageService.update(
        		communityImage, communityImageForm, getCurrentUser());
		return SUCCESS;
    }

	public void setId(Integer id) {
		this.id = id;
	}

	public CommunityImageForm getCommunityImageForm() {
		return communityImageForm;
	}

	public void setCommunityImageForm(CommunityImageForm communityImageForm) {
		this.communityImageForm = communityImageForm;
	}
	
	public CommunityImage getCommunityImage() {
		return communityImage;
	}
}