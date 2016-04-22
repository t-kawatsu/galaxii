package com.galaxii.front.form;

import java.net.URL;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class YoutubeSearchUrlForm extends AbstractForm {
	
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
			as.addFieldError("youtubeSearchUrlForm.url", as.getText("invalidate.required"));
		} else if(100 < getUrl().length()) {
			as.addFieldError("youtubeSearchUrlForm.url", as.getText("invalidate.maxLength", null, Arrays.asList("100")));
		} else {
			try {
				URL url = new URL(getUrl());
				String host = url.getHost();
				if(ArrayUtils.contains(new String[]{
						"www.youtube.com", "youtube.com", "www.youtu.be", "youtu.be"}, host)) {
					
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				as.addFieldError("youtubeSearchUrlForm.url", as.getText("invalidate.valueForm"));
			}
		}
		return !as.hasFieldErrors();
	}
}
