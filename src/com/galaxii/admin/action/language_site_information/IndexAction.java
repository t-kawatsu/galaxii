package com.galaxii.admin.action.language_site_information;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value = "language-site-information/index", results = { @Result(name = "success", location = "language-site-information/index.ftl") })
	public String execute() throws Exception {
        return SUCCESS;
	}
}
