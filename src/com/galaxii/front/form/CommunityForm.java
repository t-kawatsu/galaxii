package com.galaxii.front.form;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.galaxii.common.entity.CommunityCategory;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class CommunityForm extends AbstractImageContentsForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String CATEGORY_SETTING_CODE = "C006";
	private static final int REGIST_TAGS_LIMIT = 8;
	
	private String title;
	private String description;
	private CommunityCategory categoryId;
	
	private String communityTagsCsv;

	private Map<String, String> categories;
	
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

	public CommunityCategory getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(CommunityCategory categoryId) {
		this.categoryId = categoryId;
	}

	public String[] getCommunityTags() {
		return StringUtils.split(getCommunityTagsCsv(), ",");
	}

	public String getCommunityTagsCsv() {
		return communityTagsCsv;
	}
	
	public void setCommunityTagsCsv(String communityTagsCsv) {
		this.communityTagsCsv = communityTagsCsv;
	}

	public Map<String, String> getCategories() {
		if(categories == null) {
			categories = getEnumSelectList(CommunityCategory.class, CATEGORY_SETTING_CODE);
		}
		return categories;
	}

	public boolean validate(ActionSupport as) {
		// title
		if(StringUtils.isEmpty(getTitle())) {
			as.addFieldError("communityForm.title", as.getText("invalidate.required"));
		} else if(100 < getTitle().length()) {
			as.addFieldError("communityForm.title", as.getText("invalidate.maxLength", null, Arrays.asList("100")));
		}

		// description 
		if(StringUtils.isEmpty(getDescription())) {
			as.addFieldError("communityForm.description", as.getText("invalidate.required"));
		} else if(1500 < getDescription().length()) {
			as.addFieldError("communityForm.description", as.getText("invalidate.maxLength", null, Arrays.asList("500")));
		}

		// category id
		if(!getCategories().containsKey(getCategoryId().toString())) {
			as.addFieldError("communityForm.categoryId", as.getText("invalidate.unKnownValue"));
		}
		
		// community tags
		String[] tags = getCommunityTags();
		if(tags == null || 0 == tags.length) {
			as.addFieldError("communityForm.communityTags", as.getText("invalidate.required"));
		} else if(REGIST_TAGS_LIMIT < tags.length) {
			as.addFieldError("communityForm.communityTags", as.getText("invalidate.registLimit", Arrays.asList(REGIST_TAGS_LIMIT)));
		} else {
			for(String row : tags) {
				if(10 < row.length()) {
					as.addFieldError("communityForm.communityTags", as.getText("invalidate.maxLength", Arrays.asList(10)));
				}
			}
		}
		
		// community images
		/*
		List<ImageMeta> imageMetas = getImageMetas();
		if(imageMetas == null || 0 == imageMetas.size()) {
			as.addFieldError("communityForm.communityImages", as.getText("invalidate.required"));
		}
		*/
		return !as.hasFieldErrors();
	}
}
