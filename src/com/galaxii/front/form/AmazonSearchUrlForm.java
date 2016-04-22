package com.galaxii.front.form;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class AmazonSearchUrlForm extends AbstractForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean validate(ActionSupport as) {
		// word
		if(StringUtils.isEmpty(getUrl())) {
			as.addFieldError("amazonSearchUrlForm.url", as.getText("invalidate.required"));
		} else if(100 < getUrl().length()) {
			as.addFieldError("amazonSearchUrlForm.url", as.getText("invalidate.maxLength", null, Arrays.asList("100")));
		}
		return !as.hasFieldErrors();
	}
}
