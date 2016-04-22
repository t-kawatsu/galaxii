package com.galaxii.common.service.facebook;

/**
 * https://developers.facebook.com/docs/reference/dialogs/
 * @author t-kawatsu
 *
 */
public enum DisplayMode {
	TOUCH("touch"),
	PAGE("page"),
	POPUP("popup"),
	IFRAME("iframe"),
	ASYNC("async");
	
	private String name;
	
	DisplayMode(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
