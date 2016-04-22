package com.galaxii.common.dto;

import java.net.URL;
import java.util.List;

public class SiteData {
	
	private String title;
	private String description;
	private List<URL> imageURLs;
	private String url;
	
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
	public List<URL> getImageURLs() {
		return imageURLs;
	}
	public void setImageURLs(List<URL> imageURLs) {
		this.imageURLs = imageURLs;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
