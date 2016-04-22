package com.galaxii.front.form;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.galaxii.common.dao.UserDao;
import com.galaxii.common.entity.UserStatus;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class LoginForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@javax.annotation.Resource
	private UserDao userDao;
	
	private String mailAddress;
	private String password;
	private boolean isKeep;
	
	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isKeep() {
		return isKeep;
	}

	public void setKeep(boolean isKeep) {
		this.isKeep = isKeep;
	}

	public boolean validate(ActionSupport as) {
		// mail address
		if(StringUtils.isEmpty(getMailAddress())) {
			as.addFieldError("loginForm.mailAddress", as.getText("invalidate.required"));
		}

		// password
		if(StringUtils.isEmpty(getPassword())) {
			as.addFieldError("loginForm.password", as.getText("invalidate.required"));
		}

		if(as.hasFieldErrors()) {
			return false;
		}

		try {
			int cnt = userDao.countByMailAddressAndPasswordAndStatus(
				getMailAddress(), getPassword(), UserStatus.LIVE);
			if(cnt <= 0) {
				as.addFieldError("loginForm.mailAddress", as.getText("invalidate.login"));
			}

		} catch (Exception e) {
			as.addFieldError("loginForm.mailAddress", as.getText("invalidate.login"));
		}
	
		return !as.hasFieldErrors();
	}
}
