package com.galaxii.admin.action.language_help;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;
import com.galaxii.admin.form.LanguageHelpForm;
import com.galaxii.common.dao.LanguageHelpDao;
import com.galaxii.common.entity.LanguageHelp;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@javax.annotation.Resource
	private LanguageHelpDao languageHelpDao;
	@javax.annotation.Resource
	private LanguageHelpForm languageHelpForm;
	private Integer id;

	@Action(value="language-help/create",
		results={
			@Result(name="input", location="language-help/create.ftl"),
			@Result(name="success", location="language-help/read/${id}", type="redirect")
		}
	)
    public String execute() throws Exception {
		if(!"POST".equals(this.request.getMethod())) {
			return INPUT;
		}
		if(!languageHelpForm.validate(this)) {
			return INPUT;
		}

		LanguageHelp languageHelp = new LanguageHelp();
		BeanUtils.copyProperties(languageHelp, languageHelpForm);
		id = (Integer)languageHelpDao.save(languageHelp);
        return SUCCESS;
    }
	
	public Integer getId() {
		return id;
	}

	public void setLanguageHelpForm(LanguageHelpForm languageHelpForm) {
		this.languageHelpForm = languageHelpForm;
	}

	public LanguageHelpForm getLanguageHelpForm() {
		return languageHelpForm;
	}
}
