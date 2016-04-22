package com.galaxii.admin.action.language_help;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;
import com.galaxii.common.dao.LanguageHelpDao;
import com.galaxii.common.entity.LanguageHelp;

public class ReadAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private LanguageHelpDao languageHelpDao;
	private LanguageHelp languageHelp;
	private Integer id;

	@Action(value="language-help/read/{id}",
		results={@Result(name="success", location="language-help/read.ftl")}
	)
    public String execute() throws Exception {
		languageHelp = languageHelpDao.findById(id);
		if(languageHelp == null) {
			throw new Exception();
		}
        return SUCCESS;
    }

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	public LanguageHelp getLanguageHelp() {
		return languageHelp;
	}

}
