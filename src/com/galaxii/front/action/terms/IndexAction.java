package com.galaxii.front.action.terms;

import org.apache.struts2.convention.annotation.Action;

import com.galaxii.front.action.AbstractAction;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value="terms")
	@Override
    public String execute() throws Exception {
        return SUCCESS;
    }

	@Action(value="terms/privacy")
    public String privacyAction() throws Exception {
        return SUCCESS;
    }

}
