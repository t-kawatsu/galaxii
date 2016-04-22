package com.galaxii.front.form;

import java.util.Map;
import java.util.Arrays;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.commons.lang3.StringUtils;

import com.galaxii.front.form.AbstractForm;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@Component
public class InquiryForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String INQUIRY_TYPE_SETTING_CODE = "C002";

	private String typeId;
	private String mailAddress;
	private String description;
	private Map<String, String> types;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getTypes() {
		if(types == null) {
			types = getSettingSelectList(INQUIRY_TYPE_SETTING_CODE);
		}
		return types;
	}

	public boolean validate(ActionSupport as) {
		// mail address
		if(StringUtils.isEmpty(getMailAddress())) {
			as.addFieldError("inquiryForm.mailAddress", as.getText("invalidate.required"));
		} else if(!getMailAddress().matches("[\\w\\.\\-]+@(?:[\\w\\-]+\\.)+[\\w\\-]+")) {
			as.addFieldError("inquiryForm.mailAddress", as.getText("invalidate.email"));
		} else if(100 < getMailAddress().length()) {
			as.addFieldError("inquiryForm.mailAddress", as.getText("invalidate.maxLength", null, Arrays.asList("100")));
		}

		// description 
		if(StringUtils.isEmpty(getDescription())) {
			as.addFieldError("inquiryForm.description", as.getText("invalidate.required"));
		} else if(1500 < getDescription().length()) {
			as.addFieldError("inquiryForm.description", as.getText("invalidate.maxLength", null, Arrays.asList("1500")));
		}

		// type id 
		if(!getTypes().containsKey(getTypeId())) {
			as.addFieldError("inquiryForm.typeId", as.getText("invalidate.unKnownValue"));
		}

		return !as.hasFieldErrors();
	}
}
