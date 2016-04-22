package com.galaxii.common.dto;

import java.io.Serializable;
import java.util.List;

import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityCategory;

public class CategorizedCommunity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CommunityCategory categoryId;
	private Long count;
	private List<Community> communities;
	
	public CommunityCategory getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(CommunityCategory categoryId) {
		this.categoryId = categoryId;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
	
	public List<Community> getCommunities() {
		return communities;
	}
	
	public void setCommunities(List<Community> communities) {
		this.communities = communities;
	}
	
	
}
