package com.galaxii.common.entity;

public enum UserBgImageSize implements ImageSize {
	SS("ss", 300, 77),
    M("m", 700, 180);
    
    private String name;
    private Integer width;
    private Integer height;
    
    private UserBgImageSize(String name, Integer width, Integer height) {
    	this.name = name;
    	this.width = width;
    	this.height = height;
    }
    
    public String getName() {
    	return name;
    }
    
    public Integer getWidth() {
    	return width;
    }
    
    public Integer getHeight() {
    	return height;
    }
    
    public static UserBgImageSize nameOf(String name) {
    	for(UserBgImageSize is : values()) {
    		if(is.getName().equals(name)) {
    			return is;
    		}
    	}
    	return null;
    }
}
