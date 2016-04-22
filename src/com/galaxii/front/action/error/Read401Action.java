package com.galaxii.front.action.error;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.front.action.AbstractAction;

public class Read401Action extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value="error/401-ajax",
		results={
			@Result(name="success", location="error/401-ajax.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		return SUCCESS;
    }
}