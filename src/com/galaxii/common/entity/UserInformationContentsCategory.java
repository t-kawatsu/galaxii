package com.galaxii.common.entity;

public enum UserInformationContentsCategory {
	UNDEFINED("undefined"),
	COMMUNITY("community"),
	COMMUNITY_JOIN("community-join"),
	COMMUNITY_COMMENT("community-comment"),
	COMMUNITY_COMMENT_LIKE("community-comment-like"),
	COMMUNITY_IMAGE("community-image"),
	COMMUNITY_IMAGE_LIKE("community-image-like"),
	COMMUNITY_IMAGE_COMMENT("community-image-comment"),
	COMMUNITY_IMAGE_COMMENT_LIKE("community-image-comment-like"),
	COMMUNITY_RECOMMEND("community-recommend"),
	COMMUNITY_RECOMMEND_LIKE("community-recommend-like"),
	COMMUNITY_RECOMMEND_COMMENT("community-recommend-comment"),
	COMMUNITY_RECOMMEND_COMMENT_LIKE("community-recommend-comment-like"),
	COMMUNITY_TOPIC("community-topic"),
	COMMUNITY_TOPIC_LIKE("community-topic-like"),
	COMMUNITY_TOPIC_COMMENT("community-topic-comment"),
	COMMUNITY_TOPIC_COMMENT_LIKE("community-topic-comment-like"),
	COMMUNITY_EVENT("community-event"),
	COMMUNITY_EVENT_USER("community-event-user"),
	COMMUNITY_EVENT_LIKE("community-event-like"),
	COMMUNITY_EVENT_COMMENT("community-event-comment"),
	COMMUNITY_EVENT_COMMENT_LIKE("community-event-comment-like"),
	WATCH("watch"),
	USER_MESSAGE("user-message");
	
	private String name;
	
	private UserInformationContentsCategory(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
