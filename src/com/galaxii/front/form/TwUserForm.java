package com.galaxii.front.form;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.galaxii.common.entity.Sex;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class TwUserForm extends UserForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long twId;
	
	public Long getTwId() {
		return twId;
	}

	public void setTwId(Long twId) {
		this.twId = twId;
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
		// tw id
		if(getTwId() == null) {
			as.addFieldError("userForm.twId", as.getText("invalidate.required"));
		}
		
		// nickname
		if(StringUtils.isEmpty(getNickname())) {
			as.addFieldError("userForm.nickname", as.getText("invalidate.required"));
		} else if(getNickname().length() < 2 || 20 < getNickname().length()) {
			as.addFieldError("userForm.nickname", as.getText("invalidate.between", null, Arrays.asList("2", "20")));
		}

		return !as.hasFieldErrors();
	}
}
