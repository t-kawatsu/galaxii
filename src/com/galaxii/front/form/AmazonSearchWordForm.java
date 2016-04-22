package com.galaxii.front.form;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class AmazonSearchWordForm extends AbstractForm {
	
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
	
	@Override
	public boolean validate(ActionSupport as) {
		// word
		if(StringUtils.isEmpty(getWord())) {
			as.addFieldError("amazonSearchWordForm.word", as.getText("invalidate.required"));
		} else if(100 < getWord().length()) {
			as.addFieldError("amazonSearchWordForm.word", as.getText("invalidate.maxLength", null, Arrays.asList("100")));
		}
		return !as.hasFieldErrors();
	}
}
