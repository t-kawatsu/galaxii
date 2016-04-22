package com.galaxii.common.entity;

public enum CommunityContentsCategory {
	UNDEFINED("undefined"),
	HOME("home"),
	IMAGE("image"),
	MOVIE("movie"),
	RECOMMEND("recommend"),
	REVIEW("review"),
	COMMENT("comment"),
	EVENT("event");
	
	private String name;
	
	private CommunityContentsCategory(String name) {
		this.name = name;
	}
	
    public static CommunityContentsCategory nameOf(String name) {
    	for(CommunityContentsCategory is : values()) {
    		if(is.getName().equals(name)) {
    			return is;
    		}
    	}
    	return null;
    }
    
    public String getName() {
    	return name;
    }
}
