package com.galaxii.admin.action.language_setting;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;
import com.galaxii.admin.form.LanguageSettingForm;
import com.galaxii.common.dao.LanguageSettingDao;
import com.galaxii.common.entity.LanguageSettingId;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private LanguageSettingDao languageSettingDao;
	@Resource
	private LanguageSettingForm languageSettingForm;

	@Action(value="language-setting/create",
		results={
			@Result(name="input", location="language-setting/create.ftl"),
			@Result(name="success", location="language-setting/read", type="redirect", params={"code", "${languageSettingForm.code}", "languageCode", "${languageSettingForm.languageCode}"})
		}
	)
    public String execute() throws Exception {
		if(!"POST".equals(this.request.getMethod())) {
			return INPUT;
		}
		if(!languageSettingForm.validate(this)) {
			return INPUT;
		}

		LanguageSettingId id = new LanguageSettingId();
		id.setCode(languageSettingForm.getCode());
		id.setLanguageCode(languageSettingForm.getLanguageCode());
		languageSettingDao.save(id, languageSettingForm.getContents());

        return SUCCESS;
    }

	public void setLanguageSettingForm(LanguageSettingForm languageSettingForm) {
		this.languageSettingForm = languageSettingForm;
	}

	public LanguageSettingForm getLanguageSettingForm() {
		return languageSettingForm;
	}
}
