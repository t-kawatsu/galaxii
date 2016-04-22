package com.galaxii.admin.action.error;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;

public class ReadAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value = "error/500", results = { @Result(name = "success", location = "error/500.ftl") })
	public String execute() throws Exception {
		return SUCCESS;
	}

	@Action(value = "error/404", results = { @Result(name = "success", location = "error/404.ftl") })
	public String error404Action() throws Exception {
		return SUCCESS;
	}
}
