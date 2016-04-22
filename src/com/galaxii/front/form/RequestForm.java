package com.galaxii.front.form;

import java.util.Arrays;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.commons.lang3.StringUtils;

import com.galaxii.front.form.AbstractForm;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@Component
public class RequestForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean validate(ActionSupport as) {
		if(StringUtils.isEmpty(getDescription())) {
			as.addFieldError("requestForm.description", as.getText("invalidate.required"));
		} else if(1500 < getDescription().length()) {
			as.addFieldError("requestForm.description", as.getText("invalidate.maxLength", null, Arrays.asList("1500")));
		}
		return !as.hasFieldErrors();
	}
}
