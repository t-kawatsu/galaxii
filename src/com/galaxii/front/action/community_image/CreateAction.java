package com.galaxii.front.action.community_image;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.entity.CommunityImage;
import com.galaxii.common.entity.ImageType;
import com.galaxii.common.entity.User;
import com.galaxii.common.service.CommunityActivityService;
import com.galaxii.common.service.CommunityImageService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.CommunityImageForm;
import com.galaxii.front.form.ImageForm;

public class CreateAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private ImageForm imageForm;
	@Resource
	private CommunityImageForm communityImageForm;
	
	@Resource
	private CommunityImageService communityImageService;
	@Resource
	private CommunityActivityService communityActivityService;
	@Resource
	private CommunityUserDao communityUserDao;
	
	private Map<String,Object> uploadResultJson;
	
	private Integer communityId;
	
	@Action(value="community-image/create-tmp-ajax",
			results={
			@Result(name="input", location="community-image/create-tmp.ftl"),
            @Result(name="success", location="community-image/create-tmp.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!imageForm.validate(this)) {
            return INPUT;
        }
		return SUCCESS;
    }
	
	@Action(value="community-image/create-ajax",
			results={
			@Result(name="input", location="community-image/create.ftl"),
            @Result(name="success", location="community-image/create-complete.ftl")
		}
	)
    public String createAjaxAction() throws Exception {
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
        // create
        CommunityImage communityImage = communityImageService.create(
        		communityId, communityImageForm, getCurrentUser());
        // activity
        communityActivityService
        	.insertCommunityImageActivity(communityImage);
        // user info
        userInformationService
        	.insertCommunityImage(communityId, getCurrentUser());
		return SUCCESS;
    }
	
	@Action(value="community-image/upload-ajax",
		results={
			@Result(name="success", type="json", params={
					"statusCode", "200",
					"contentType", "text/html",
					"noCache", "true",
					"root", "uploadResultJson"
					}),
			@Result(name="login", type="json", params={
					"statusCode", "401",
					"contentType", "text/html",
					"noCache", "true",
					"root", "uploadResultJson"
					}),
			@Result(name="input", type="json", params={
					"statusCode", "200",
					"contentType", "text/html",
					"noCache", "true",
					"root", "uploadResultJson"
					})
			/*
			@Result(name="success", type="httpheader", params={
					"status", "200",
					"headers.result", "${uploadResultJson}"
					})
			*/
		}
	)
	public String uplodAjaxAction() throws Exception {
		uploadResultJson = new HashMap<String, Object>();
		if(!getIsLogined()) {
			return LOGIN;
		}
        if(!imageForm.validate(this)) {
        	uploadResultJson.put("messages", getFieldErrors());
            return INPUT;
        }
        User user = getCurrentUser();
        String fileId = communityImageService.storeTmpImage(imageForm.getFile(), user.getId());
        uploadResultJson = communityImageService
        		.formatTmpCreatedData(fileId, ImageType.IMAGE, null);
		return SUCCESS;
	}

	public ImageForm getImageForm() {
		return imageForm;
	}

	public void setImageForm(ImageForm imageForm) {
		this.imageForm = imageForm;
	}
	
	public Map<String,Object> getUploadResultJson() {
		return uploadResultJson;
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