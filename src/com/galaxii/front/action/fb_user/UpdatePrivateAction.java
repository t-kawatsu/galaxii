package com.galaxii.front.action.fb_user;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.User;

public class UpdatePrivateAction extends AbstractFbAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value="fb-user/update-private-ajax",
		results={
			@Result(name="success", location="fb-user/_btn-private.ftl")
		}
	)
    public String execute() throws Exception {
		User user = getCurrentUser();
		if(user == null || !user.isFacebookUser()) {
			return LOGIN;
		}
		fbUserService.toggleUserPrivate(user);
		setCurrentUser(user);
        return SUCCESS;
    }
	
	public User getUser() {
		return getCurrentUser();
	}
}
