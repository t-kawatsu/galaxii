package com.galaxii.front.action.user_image;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.User;
import com.galaxii.common.service.UserBgImageService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.ImageForm;
import com.galaxii.front.form.UserImageForm;

public class CreateBgAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private ImageForm imageForm;
	@Resource
	private UserImageForm userImageForm;
	@Resource
	private UserBgImageService userBgImageService;
	
	private Map<String,Object> uploadResultJson;
	
	@Action(value="user-image/create-bg-ajax",
			results={
			@Result(name="input", location="user-image/create-bg.ftl"),
            @Result(name="success", location="user-image/create-bg-complete.ftl")
		}
	)
    public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }
        if(!userImageForm.validate(this)) {
        	return ERROR;
        }
        User user = getCurrentUser();
        user = userBgImageService.create(user, userImageForm);
        setCurrentUser(user);
		return SUCCESS;
    }
	
	@Action(value="user-image/upload-bg-ajax",
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
        String fileId = userBgImageService.storeTmpImage(imageForm.getFile(), user.getId());
        uploadResultJson = userBgImageService.formatTmpCreatedData(fileId);
		return SUCCESS;
	}

	public ImageForm getImageForm() {
		return imageForm;
	}

	public void setImageForm(ImageForm imageForm) {
		this.imageForm = imageForm;
	}
	
	public void setUserImageForm(UserImageForm userImageForm) {
		this.userImageForm = userImageForm;
	}
	
	public Map<String,Object> getUploadResultJson() {
		return uploadResultJson;
	}
	
	public User getUser() {
		return getCurrentUser();
	}
}