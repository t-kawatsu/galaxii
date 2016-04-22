package com.galaxii.front.form;

import java.util.Arrays;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.commons.lang3.StringUtils;

import com.galaxii.common.dao.UserDao;
import com.galaxii.common.entity.UserStatus;
import com.galaxii.front.form.AbstractForm;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@Component
public class UserMailAddressForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mailAddress;
	@javax.annotation.Resource
	private UserDao userDao;
	
	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public boolean validate(ActionSupport as) {
		// mail address
		if(StringUtils.isEmpty(getMailAddress())) {
			as.addFieldError("userMailAddressForm.mailAddress", as.getText("invalidate.required"));
		} else if(!getMailAddress().matches("[\\w\\.\\-]+@(?:[\\w\\-]+\\.)+[\\w\\-]+")) {
			as.addFieldError("userMailAddressForm.mailAddress", as.getText("invalidate.email"));
		} else if(100 < getMailAddress().length()) {
			as.addFieldError("userMailAddressForm.mailAddress", as.getText("invalidate.maxLength", null, Arrays.asList("100")));
		} else {
			if(0 < userDao.countByMailAddressAndStatus(getMailAddress(), UserStatus.LIVE)) { //, UserAccountType.PROPER)) {
				as.addFieldError("userMailAddressForm.mailAddress", as.getText("invalidate.alreadyExists"));
			}
		}

		return !as.hasFieldErrors();
	}
}
