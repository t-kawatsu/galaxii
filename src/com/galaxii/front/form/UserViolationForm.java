package com.galaxii.front.form;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.galaxii.common.entity.UserViolationCategory;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class UserViolationForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String TYPE_SETTING_CODE = "C007";
	
	private Integer typeId;
	private String description;
	private UserViolationCategory category;
	private Integer categoryDataId;
	
	private  Map<String, String> types;
	
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserViolationCategory getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = UserViolationCategory.nameOf(category);
	}

	public Integer getCategoryDataId() {
		return categoryDataId;
	}

	public void setCategoryDataId(Integer categoryDataId) {
		this.categoryDataId = categoryDataId;
	}

	public Map<String, String> getTypes() {
		if(types == null) {
			types = getSettingSelectList(TYPE_SETTING_CODE);
		}
		return types;
	}

	public boolean validate(ActionSupport as) {
		// description 
		if(StringUtils.isEmpty(getDescription())) {
			as.addFieldError("userViolationForm.description", as.getText("invalidate.required"));
		} else if(100 < getDescription().length()) {
			as.addFieldError("userViolationForm.description", as.getText("invalidate.maxLength", null, Arrays.asList("100")));
		}

		// type id 
		if(!getTypes().containsKey(String.valueOf(getTypeId()))) {
			as.addFieldError("userViolationForm.typeId", as.getText("invalidate.unKnownValue"));
		}

		// category data id 
		if(getCategoryDataId() == null) {
			as.addFieldError("userViolationForm.categoryDataId", as.getText("invalidate.unKnownValue"));
		}
		
		// category data id 
		if(getCategory() == null) {
			as.addFieldError("userViolationForm.category", as.getText("invalidate.unKnownValue"));
		}
		
		return !as.hasFieldErrors();
	}
}
