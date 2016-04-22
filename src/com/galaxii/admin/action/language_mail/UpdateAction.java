package com.galaxii.admin.action.language_mail;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.commons.beanutils.BeanUtils;

import com.galaxii.admin.action.AbstractAction;
import com.galaxii.admin.form.LanguageMailForm;
import com.galaxii.common.dao.LanguageMailDao;
import com.galaxii.common.entity.LanguageMail;

public class UpdateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private LanguageMailDao languageMailDao;
	@javax.annotation.Resource
	private LanguageMailForm languageMailForm;

	@Action(value="language-mail/update",
		results={
			@Result(name="input", location="language-mail/create.ftl"),
			@Result(name="success", location="language-mail/read", type="redirect", params={"code", "${languageMailForm.code}", "languageCode", "${languageMailForm.languageCode}"})
		}
	)
    public String execute() throws Exception {
		LanguageMail languageMail = languageMailDao.findByCode(
			languageMailForm.getCode(), languageMailForm.getLanguageCode());
		if(languageMail == null) {
			throw new Exception();
		}

		if(!"POST".equals(this.request.getMethod())) {
			BeanUtils.copyProperties(languageMailForm, languageMail.getId());
			BeanUtils.copyProperties(languageMailForm, languageMail);
			return INPUT;
		}
		if(!languageMailForm.validate(this)) {
			return INPUT;
		}

		BeanUtils.copyProperties(languageMail.getId(), languageMailForm);
		BeanUtils.copyProperties(languageMail, languageMailForm);
		languageMailDao.update(languageMail);

        return SUCCESS;
    }

	public void setLanguageMailForm(LanguageMailForm languageMailForm) {
		this.languageMailForm = languageMailForm;
	}

	public LanguageMailForm getLanguageMailForm() {
		return languageMailForm;
	}

	public boolean getIsUpdate() {
		return true;
	}
}
