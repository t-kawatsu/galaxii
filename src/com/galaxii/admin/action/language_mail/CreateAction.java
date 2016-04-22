package com.galaxii.admin.action.language_mail;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;
import com.galaxii.admin.form.LanguageMailForm;
import com.galaxii.common.dao.LanguageMailDao;
import com.galaxii.common.entity.LanguageMail;
import com.galaxii.common.entity.LanguageMailId;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private LanguageMailDao languageMailDao;
	@javax.annotation.Resource
	private LanguageMailForm languageMailForm;

	@Action(value="language-mail/create",
		results={
			@Result(name="input", location="language-mail/create.ftl"),
			@Result(name="success", location="language-mail/read", type="redirect", params={"code", "${languageMailForm.code}", "languageCode", "${languageMailForm.languageCode}"})
		}
	)
    public String execute() throws Exception {
		if(!"POST".equals(this.request.getMethod())) {
			return INPUT;
		}
		if(!languageMailForm.validate(this)) {
			return INPUT;
		}

		LanguageMail languageMail = new LanguageMail();
		LanguageMailId languageMailId = new LanguageMailId();
		BeanUtils.copyProperties(languageMailId, languageMailForm);
		BeanUtils.copyProperties(languageMail, languageMailForm);
		languageMail.setId(languageMailId);
		languageMailDao.save(languageMail);

        return SUCCESS;
    }

	public void setLanguageMailForm(LanguageMailForm languageMailForm) {
		this.languageMailForm = languageMailForm;
	}

	public LanguageMailForm getLanguageMailForm() {
		return languageMailForm;
	}
}
