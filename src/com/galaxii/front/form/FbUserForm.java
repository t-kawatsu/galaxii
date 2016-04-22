package com.galaxii.front.form;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.galaxii.common.entity.Sex;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class FbUserForm extends UserForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fbId;

	public String getFbId() {
		return fbId;
	}
	
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	
	public void setGender(String genderName) {
		if(genderName == null) {
			return;
		}
		if("male".equals(genderName)) {
			this.setSex(Sex.MALE);
		} else if("female".equals(genderName)) {
			this.setSex(Sex.FEMALE);
		}
	}

	public boolean validate(ActionSupport as) {
		// fb id
		if(getFbId() == null) {
			as.addFieldError("userForm.fbId", as.getText("invalidate.required"));
		}
		
		// nickname
		if(StringUtils.isEmpty(getNickname())) {
			as.addFieldError("userForm.nickname", as.getText("invalidate.required"));
		} else if(getNickname().length() < 2 || 20 < getNickname().length()) {
			as.addFieldError("userForm.nickname", as.getText("invalidate.between", null, Arrays.asList("2", "20")));
		}
		
		// mail address
		if(StringUtils.isEmpty(getMailAddress())) {
			as.addFieldError("userForm.mailAddress", as.getText("invalidate.required"));
		} else if(100 < getMailAddress().length()) {
			as.addFieldError("userForm.mailAddress", as.getText("invalidate.maxLength", null, Arrays.asList("100")));
		} 
		
		// sex
		if(getSex() == null) {
			as.addFieldError("userForm.sex", as.getText("invalidate.required"));
		}
		
		return !as.hasFieldErrors();
	}
}
