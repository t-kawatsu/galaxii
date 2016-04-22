package com.galaxii.common.service.dig_i;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InformationItem {

	private Integer id;
	private Integer resourceId;
	private String title;
	private String link;
	private String description;
	private String imageUrl;
	private Date publishedAt;
	private List<String> categories;
	private Locale languageCode;
	private Date createdAt;
	
	private String resourceTitle;
	private String resourceLink;
	private String resourceType;
	private String resourceImageUrl;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Date getPublishedAt() {
		return publishedAt;
	}
	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public Locale getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(Locale languageCode) {
		this.languageCode = languageCode;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getResourceTitle() {
		return resourceTitle;
	}
	public void setResourceTitle(String resourceTitle) {
		this.resourceTitle = resourceTitle;
	}
	public String getResourceLink() {
		return resourceLink;
	}
	public void setResourceLink(String resourceLink) {
		this.resourceLink = resourceLink;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourceImageUrl() {
		return resourceImageUrl;
	}
	public void setResourceImageUrl(String resourceImageUrl) {
		this.resourceImageUrl = resourceImageUrl;
	}
}
