package com.galaxii.admin.form;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.galaxii.front.form.AbstractForm;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class AdminLoginForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean validate(ActionSupport as) {
		// mail address
		if(StringUtils.isEmpty(getName())) {
			as.addFieldError("adminLoginForm.name", as.getText("invalidate.required"));
		}

		// password
		if(StringUtils.isEmpty(getPassword())) {
			as.addFieldError("adminLoginForm.password", as.getText("invalidate.required"));
		}

		if(as.hasFieldErrors()) {
			return false;
		}

		return !as.hasFieldErrors();
	}
}
