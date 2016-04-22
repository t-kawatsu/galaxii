package com.galaxii.admin.action.language_setting;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;
import com.galaxii.common.dao.LanguageSettingDao;
import com.galaxii.common.entity.LanguageSetting;
import com.galaxii.common.entity.LanguageSettingId;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private LanguageSettingDao languageSettingDao;
	
	private LanguageSettingId id;
	
	@Action(value="language-setting/delete",
		results={
			@Result(name="input", location="language-setting/delete.ftl"),
			@Result(name="success", location="language-setting/index", type="redirect")
		}
	)
    public String execute() throws Exception {
		LanguageSetting languageSetting = 
				languageSettingDao.findByCode(id.getCode());
		if(languageSetting == null) {
			throw new Exception();
		}

		languageSettingDao.delete(languageSetting);

        return SUCCESS;
    }

	public void setId(LanguageSettingId id) {
		this.id = id;
	}

	public LanguageSettingId getId() {
		return id;
	}

	public boolean getIsUpdate() {
		return true;
	}
}
