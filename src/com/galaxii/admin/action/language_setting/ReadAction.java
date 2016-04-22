package com.galaxii.admin.action.language_setting;

import java.util.Locale;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;
import com.galaxii.common.dao.LanguageSettingDao;
import com.galaxii.common.entity.LanguageSetting;

public class ReadAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private LanguageSettingDao languageSettingDao;
	private LanguageSetting languageSetting;
	private String code;
	private String languageCode;

	@Action(value="language-setting/read",
		results={@Result(name="success", location="language-setting/read.ftl")}
	)
    public String execute() throws Exception {
		languageSetting = languageSettingDao.findByCode(code, new Locale(languageCode));
		if(languageSetting == null) {
			throw new Exception();
		}
        return SUCCESS;
    }

	public void setCode(String code) {
		this.code = code;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public LanguageSetting getLanguageSetting() {
		return languageSetting;
	}

}
