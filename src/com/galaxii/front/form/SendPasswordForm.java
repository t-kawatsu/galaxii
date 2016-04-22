package com.galaxii.front.form;

import java.util.Arrays;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.commons.lang3.StringUtils;

import com.galaxii.front.form.AbstractForm;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@Component
public class SendPasswordForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mailAddress;
	
	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public boolean validate(ActionSupport as) {
		// mail address
		if(StringUtils.isEmpty(getMailAddress())) {
			as.addFieldError("sendPasswordForm.mailAddress", as.getText("invalidate.required"));
		} else if(!getMailAddress().matches(
			"([a-zA-Z0-9][a-zA-Z0-9_.+\\-]*)@(([a-zA-Z0-9][a-zA-Z0-9_\\-]+\\.)+[a-zA-Z]{2,6})")) {
			as.addFieldError("sendPasswordForm.mailAddress", as.getText("invalidate.email"));
		} else if(100 < getMailAddress().length()) {
			as.addFieldError("sendPasswordForm.mailAddress", as.getText("invalidate.maxLength", null, Arrays.asList("100")));
		}

		return !as.hasFieldErrors();
	}
}
