package com.galaxii.front.action.user_violation;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.UserViolation;
import com.galaxii.common.service.UserViolationService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.UserViolationForm;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private UserViolationForm userViolationForm;
	@Resource
	private UserViolationService userViolationService;
	
	private UserViolation userViolation;

	@Action(value="user-violation/create-ajax/{userViolationForm.category}/{userViolationForm.categoryDataId}",
		results={
			 @Result(name="input", location="user-violation/create.ftl"),
            @Result(name="success", location="user-violation/create-complete.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }
        if(!userViolationForm.validate(this)) {
            return INPUT;
        }
        userViolation = userViolationService
        		.create(userViolationForm, getCurrentUser().getId());
		return SUCCESS;
    }
	
	public void setUserViolationForm(UserViolationForm userViolationForm) {
		this.userViolationForm = userViolationForm;
	}
	
	public UserViolationForm getUserViolationForm() {
		return userViolationForm;
	}

	public UserViolation getUserViolation() {
		return userViolation;
	}
}