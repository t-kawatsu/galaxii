package com.galaxii.front.action.fb_user;


import org.apache.struts2.convention.annotation.Action;


public class LogoutAction extends AbstractFbAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Action(value="fb-user/logout")
    public String execute() throws Exception {
        return TOP;
    }

}
