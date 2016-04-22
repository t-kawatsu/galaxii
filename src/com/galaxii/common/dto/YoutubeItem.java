package com.galaxii.common.dto;

public class YoutubeItem {
	
	private String entryId;
	private String title;
	private String description;
	private YoutubeItemImage image;
	
	public String getEntryId() {
		return entryId;
	}
	public void setEntryId(String entryId) {
		this.entryId = entryId;
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
	public YoutubeItemImage getImage() {
		return image;
	}
	public void setImage(YoutubeItemImage image) {
		this.image = image;
	}
	
	
}
