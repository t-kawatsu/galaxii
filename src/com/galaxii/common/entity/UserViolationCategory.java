package com.galaxii.common.entity;

public enum UserViolationCategory {
	UNKNOWN("unknown"),
	COMMUNITY("community"),
	COMMUNITY_COMMENT("community-comment"),
	COMMUNITY_IMAGE("community-image"),
	COMMUNITY_IMAGE_COMMENT("community-image-comment"),
	COMMUNITY_EVENT("community-event"),
	COMMUNITY_EVENT_COMMENT("community-event-comment"),
	COMMUNITY_TOPIC("community-topic"),
	COMMUNITY_TOPIC_COMMENT("community-topic-comment"),
	COMMUNITY_RECOMMEND("community-recommend"),
	COMMUNITY_RECOMMEND_COMMENT("community-recommend-comment");
	
	private String name;
	
	private UserViolationCategory(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
    public static UserViolationCategory nameOf(String name) {
    	for(UserViolationCategory is : values()) {
    		if(is.getName().equals(name)) {
    			return is;
    		}
    	}
    	return null;
    }
}
