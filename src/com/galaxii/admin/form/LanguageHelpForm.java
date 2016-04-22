package com.galaxii.admin.form;

import java.util.Locale;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.commons.lang3.StringUtils;

import com.galaxii.front.form.AbstractForm;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@Component
public class LanguageHelpForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String description;
	private Locale languageCode;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Locale getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(Locale languageCode) {
		this.languageCode = languageCode;
	}

	public boolean validate(ActionSupport as) {
		if(StringUtils.isEmpty(title)) {
			as.addFieldError("languageHelpForm.title", as.getText("invalidate.required"));
		}

		if(StringUtils.isEmpty(getDescription())) {
			as.addFieldError("languageHelpForm.description", as.getText("invalidate.required"));
		}

		if(null == getLanguageCode()) {
			as.addFieldError("languageHelpForm.languageCode", as.getText("invalidate.required"));
		}

		return !as.hasFieldErrors();
	}
}
