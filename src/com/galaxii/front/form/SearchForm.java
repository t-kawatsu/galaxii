package com.galaxii.front.form;

import java.util.Arrays;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.commons.lang3.StringUtils;

import com.galaxii.front.form.AbstractForm;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@Component
public class SearchForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String word;
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public boolean validate(ActionSupport as) {
		// word
		if(StringUtils.isEmpty(getWord())) {
			as.addFieldError("searchForm.word", as.getText("invalidate.required"));
		} else if(20 < getWord().length()) {
			as.addFieldError("searchForm.word", as.getText("invalidate.maxLength", null, Arrays.asList("20")));
		}
		return !as.hasFieldErrors();
	}
}
