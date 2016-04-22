package com.galaxii.admin.form;

import java.util.Locale;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.commons.lang3.StringUtils;

import com.galaxii.front.form.AbstractForm;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@Component
public class LanguageSettingForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private Locale languageCode;
	private String contents;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Locale getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(Locale languageCode) {
		this.languageCode = languageCode;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}

	public boolean validate(ActionSupport as) {
		if(StringUtils.isEmpty(getCode())) {
			as.addFieldError("languageSettingForm.code", as.getText("invalidate.required"));
		}

		if(null == getLanguageCode()) {
			as.addFieldError("languageSettingForm.languageCode", as.getText("invalidate.required"));
		}

		if(StringUtils.isEmpty(getContents())) {
			as.addFieldError("languageSettingForm.body", as.getText("invalidate.required"));
		}

		return !as.hasFieldErrors();
	}
}
