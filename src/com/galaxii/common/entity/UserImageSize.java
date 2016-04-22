package com.galaxii.common.entity;

public enum UserImageSize implements ImageSize {
	SS("ss", 36, 36),
    S("s", 54, 54),
    M("m", 180, 180),
	L("l", 512, 512);
    
    private String name;
    private Integer width;
    private Integer height;
    
    private UserImageSize(String name, Integer width, Integer height) {
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
    
    public static UserImageSize nameOf(String name) {
    	for(UserImageSize is : values()) {
    		if(is.getName().equals(name)) {
    			return is;
    		}
    	}
    	return null;
    }
}
