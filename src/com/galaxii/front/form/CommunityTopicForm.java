package com.galaxii.front.form;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class CommunityTopicForm extends AbstractImageContentsForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer communityId;
	private String title;
	private String description;
	
	public Integer getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
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
			as.addFieldError("communityTopicForm.title", as.getText("invalidate.required"));
		} else if(100 < getTitle().length()) {
			as.addFieldError("communityTopicForm.title", as.getText("invalidate.maxLength", null, Arrays.asList("100")));
		}

		// description 
		if(StringUtils.isEmpty(getDescription())) {
			as.addFieldError("communityTopicForm.description", as.getText("invalidate.required"));
		} else if(1500 < getDescription().length()) {
			as.addFieldError("communityTopicForm.description", as.getText("invalidate.maxLength", null, Arrays.asList("1500")));
		}

		return !as.hasFieldErrors();
	}
	
	public boolean validateUpdate(ActionSupport as) {

		// description 
		if(StringUtils.isEmpty(getDescription())) {
			as.addFieldError("communityTopicForm.description", as.getText("invalidate.required"));
		} else if(1500 < getDescription().length()) {
			as.addFieldError("communityTopicForm.description", as.getText("invalidate.maxLength", null, Arrays.asList("500")));
		}

		return !as.hasFieldErrors();
	}
}
