package com.galaxii.admin.action.language_mail;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;
import com.galaxii.common.dao.LanguageMailDao;
import com.galaxii.common.entity.LanguageMail;
import com.galaxii.common.entity.LanguageMailId;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private LanguageMailDao languageMailDao;
	
	private LanguageMailId id;
	
	@Action(value="language-mail/delete",
		results={
			@Result(name="input", location="language-mail/delete.ftl"),
			@Result(name="success", location="language-mail/index", type="redirect")
		}
	)
    public String execute() throws Exception {
		LanguageMail languageMail = languageMailDao.findByCode(id.getCode());
		if(languageMail == null) {
			throw new Exception();
		}
		languageMailDao.delete(languageMail);

        return SUCCESS;
    }

	public void setId(LanguageMailId id) {
		this.id = id;
	}

	public LanguageMailId getId() {
		return id;
	}

	public boolean getIsUpdate() {
		return true;
	}
}
