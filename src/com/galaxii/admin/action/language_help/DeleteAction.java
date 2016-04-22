package com.galaxii.admin.action.language_help;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;
import com.galaxii.common.dao.LanguageHelpDao;
import com.galaxii.common.entity.LanguageHelp;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private LanguageHelpDao languageHelpDao;
	
	private Integer id;
	
	@Action(value="language-help/delete/{id}",
		results={
			@Result(name="input", location="language-help/delete.ftl"),
			@Result(name="success", location="language-help/index", type="redirect")
		}
	)
    public String execute() throws Exception {
		LanguageHelp languageHelp = languageHelpDao.findById(id);
		if(languageHelp == null) {
			throw new Exception();
		}
		languageHelpDao.delete(languageHelp);

        return SUCCESS;
    }

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public boolean getIsUpdate() {
		return true;
	}
}
