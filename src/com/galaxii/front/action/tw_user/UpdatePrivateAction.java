package com.galaxii.front.action.tw_user;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserAccountType;

public class UpdatePrivateAction extends AbstractTwAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value="tw-user/update-private-ajax",
		results={
			@Result(name="success", location="tw-user/_btn-private.ftl")
		}
	)
    public String execute() throws Exception {
		User user = getCurrentUser();
		if(user == null || !user.hasAccountType(UserAccountType.TWITTER)) {
			return LOGIN;
		}
		twUserService.toggleUserPrivate(user);
		setCurrentUser(user);
        return SUCCESS;
    }
	
	public User getUser() {
		return getCurrentUser();
	}
}
