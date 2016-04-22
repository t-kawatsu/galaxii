package com.galaxii.front.form;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class CommentForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer baseId;
	private String description;
	private Boolean shareTwitter;
	private Boolean shareFacebook;
	
	public Integer getBaseId() {
		return baseId;
	}
	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = StringUtils.trim(description);
	}
	public Boolean getShareTwitter() {
		return shareTwitter;
	}
	public void setShareTwitter(Boolean shareTwitter) {
		this.shareTwitter = shareTwitter;
	}
	public Boolean getShareFacebook() {
		return shareFacebook;
	}
	public void setShareFacebook(Boolean shareFacebook) {
		this.shareFacebook = shareFacebook;
	}
	
	public boolean validate(ActionSupport as) {
		// description 
		if(StringUtils.isEmpty(getDescription())) {
			as.addFieldError("commentForm.description", as.getText("invalidate.required"));
		} else if(140 < getDescription().length()) {
			as.addFieldError("commentForm.description", as.getText("invalidate.maxLength", null, Arrays.asList("140")));
		}
		return !as.hasFieldErrors();
	}
}
