package com.galaxii.admin.form;

import java.util.Locale;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.commons.lang3.StringUtils;

import com.galaxii.front.form.AbstractForm;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@Component
public class LanguageMailForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String subject;
	private String body;
	private Locale languageCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Locale getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(Locale languageCode) {
		this.languageCode = languageCode;
	}

	public boolean validate(ActionSupport as) {
		if(StringUtils.isEmpty(getCode())) {
			as.addFieldError("languageMailForm.code", as.getText("invalidate.required"));
		}

		if(StringUtils.isEmpty(getSubject())) {
			as.addFieldError("languageMailForm.subject", as.getText("invalidate.required"));
		}

		if(StringUtils.isEmpty(getBody())) {
			as.addFieldError("languageMailForm.body", as.getText("invalidate.required"));
		}

		if(null == getLanguageCode()) {
			as.addFieldError("languageMailForm.languageCode", as.getText("invalidate.required"));
		}

		return !as.hasFieldErrors();
	}
}
