package com.galaxii.front.action.user;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import com.galaxii.common.service.UserService;
import com.galaxii.front.action.AbstractAction;

public class LogoutAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private UserService userService;

	@Action(value="user/logout")
	@Override
    public String execute() throws Exception {
		userService.logout();
		sessUtil.removeCurrentUser();
        return TOP;
    }

}
