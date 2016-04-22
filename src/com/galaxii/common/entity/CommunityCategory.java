package com.galaxii.common.entity;

public enum CommunityCategory {
	UNDEFINED("undefined"),
	MOVIE("movie"),
	BOOK("book"),
	MUSIC("music"),
	GAME("game"),
	GOURMET("gourmet"),
	HOBBY("hobby"),
	PLACE("place"),
	SPORTS("sports"),
	ART("art"),
	GOODS("goods"),
	GROUP("group"),
	PERSON("person"),
	PET("pet"),
	STUDY("study"),
	TV("tv"),
	BRAND("brand"),
	OTHER("other");
	
	private String name;
	
	private CommunityCategory(String name) {
		this.name = name;
	}
	
    public static CommunityCategory nameOf(String name) {
    	for(CommunityCategory is : values()) {
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
