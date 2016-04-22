package com.galaxii.admin.action.language_setting;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;
import com.galaxii.admin.form.LanguageSettingForm;
import com.galaxii.common.dao.LanguageSettingDao;
import com.galaxii.common.entity.LanguageSetting;

public class UpdateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private LanguageSettingDao languageSettingDao;
	@Resource
	private LanguageSettingForm languageSettingForm;

	@Action(value="language-setting/update",
		results={
			@Result(name="input", location="language-setting/create.ftl"),
			@Result(name="success", location="language-setting/read", type="redirect", params={"code", "${languageSettingForm.code}", "languageCode", "${languageSettingForm.languageCode}"})
		}
	)
    public String execute() throws Exception {
		LanguageSetting languageSetting = languageSettingDao.findByCode(
			languageSettingForm.getCode(), languageSettingForm.getLanguageCode());
		if(languageSetting == null) {
			throw new Exception();
		}

		if(!"POST".equals(this.request.getMethod())) {
			BeanUtils.copyProperties(languageSettingForm, languageSetting.getId());
			BeanUtils.copyProperties(languageSettingForm, languageSetting);
			languageSettingForm.setContents(
					languageSettingDao.mapToStringContents(languageSetting.getContents()));
			return INPUT;
		}
		if(!languageSettingForm.validate(this)) {
			return INPUT;
		}

		languageSettingDao.update(
				languageSetting.getId(), languageSettingForm.getContents());

        return SUCCESS;
    }

	public void setLanguageSettingForm(LanguageSettingForm languageSettingForm) {
		this.languageSettingForm = languageSettingForm;
	}

	public LanguageSettingForm getLanguageSettingForm() {
		return languageSettingForm;
	}

	public boolean getIsUpdate() {
		return true;
	}
}
