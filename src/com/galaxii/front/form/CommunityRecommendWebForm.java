package com.galaxii.front.form;

import java.net.URL;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class CommunityRecommendWebForm extends AbstractForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String MATCH_URL = 
			  "^(https?|ftp)(:\\/\\/[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+)$";
	
	private String url;
	private Integer imageIndex;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getImageIndex() {
		return imageIndex;
	}

	public void setImageIndex(Integer imageIndex) {
		this.imageIndex = imageIndex;
	}

	@Override
	public boolean validate(ActionSupport as) {
		// TODO content-type validation && content site limitation
		// word
		if(StringUtils.isEmpty(getUrl())) {
			as.addFieldError("communityRecommendWebForm.url", as.getText("invalidate.required"));
		} else if(500 < getUrl().length()) {
			as.addFieldError("communityRecommendWebForm.url", as.getText("invalidate.maxLength", null, Arrays.asList("500")));
		} else if(!getUrl().matches(MATCH_URL)) {
			as.addFieldError("communityRecommendWebForm.url", as.getText("invalidate.url"));
		} else {
			try {
				new URL(getUrl());
			} catch (Exception e) {
				as.addFieldError("communityRecommendWebForm.url", as.getText("invalidate.valueForm"));
			}
		}
		return !as.hasFieldErrors();
	}
}
