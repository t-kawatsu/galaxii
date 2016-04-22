package com.galaxii.front.action.user_image;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.User;
import com.galaxii.common.service.UserBgImageService;
import com.galaxii.front.action.AbstractAction;

public class DeleteBgAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private UserBgImageService userBgImageService;
	
	private Integer userImageId;
	
	@Action(value="user-image/delete-bg-ajax/{userImageId}",
		results={
            @Result(name="success", location="user-image/_u-bg-img.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
        User user = getCurrentUser();
        user = userBgImageService.deleteImage(user, userImageId);
        setCurrentUser(user);
		return SUCCESS;
    }
	
	public void setUserImageId(Integer userImageId) {
		this.userImageId = userImageId;
	}
	
	public User getUser() {
		return getCurrentUser();
	}
}