package com.galaxii.front.action.user;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.service.UserService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.UserSecessionForm;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private UserService userService;
	@Resource
	private UserSecessionForm userSecessionForm;

	@Action(value = "user/delete", results = {
			@Result(name = "input", location = "user/delete.ftl"),
			@Result(name = "success", location = "user/delete-complete.ftl") })
	public String execute() throws Exception {
		if (!getIsLogined()) {
			return LOGIN;
		}
		if (!"POST".equals(request.getMethod())) {
			return INPUT;
		}
		if (!userSecessionForm.validate(this)) {
			return INPUT;
		}

		userService.secession(getCurrentUser(), 
				userSecessionForm.getReasonCode(),
				userSecessionForm.getDescription());

		userService.logout();
		sessUtil.removeCurrentUser();
		
		return SUCCESS;
	}

	public void setUserSecessionForm(UserSecessionForm userSecessionForm) {
		this.userSecessionForm = userSecessionForm;
	}

	public UserSecessionForm getUserSecessionForm() {
		return userSecessionForm;
	}

}
