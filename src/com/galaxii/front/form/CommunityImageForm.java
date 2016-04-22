package com.galaxii.front.form;

import java.util.List;
import java.util.Arrays;

import com.galaxii.common.dto.ImageMeta;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@Component
public class CommunityImageForm extends AbstractImageContentsForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String description;

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

	public boolean validate(ActionSupport as) {
		// title
		if(StringUtils.isEmpty(getTitle())) {
			as.addFieldError("communityImageForm.title", as.getText("invalidate.required"));
		} else if(100 < getTitle().length()) {
			as.addFieldError("communityImageForm.title", as.getText("invalidate.maxLength", null, Arrays.asList("100")));
		}

		// description 
		if(StringUtils.isEmpty(getDescription())) {
			//as.addFieldError("communityImageForm.description", as.getText("invalidate.required"));
		} else if(1500 < getDescription().length()) {
			as.addFieldError("communityImageForm.description", as.getText("invalidate.maxLength", null, Arrays.asList("500")));
		}
		
		// community images
		List<ImageMeta> imageMetas = getImageMetas();
		if(imageMetas == null || 0 == imageMetas.size()) {
			as.addFieldError("communityImageForm.images", as.getText("invalidate.required"));
		}

		return !as.hasFieldErrors();
	}
	
	public boolean validateUpdate(ActionSupport as) {
		// title
		if(StringUtils.isEmpty(getTitle())) {
			as.addFieldError("communityImageForm.title", as.getText("invalidate.required"));
		} else if(100 < getTitle().length()) {
			as.addFieldError("communityImageForm.title", as.getText("invalidate.maxLength", null, Arrays.asList("100")));
		}

		// description 
		if(StringUtils.isEmpty(getDescription())) {
			//as.addFieldError("communityImageForm.description", as.getText("invalidate.required"));
		} else if(1500 < getDescription().length()) {
			as.addFieldError("communityImageForm.description", as.getText("invalidate.maxLength", null, Arrays.asList("500")));
		}
		
		return !as.hasFieldErrors();
	}
}
