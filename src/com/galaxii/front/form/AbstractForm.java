package com.galaxii.front.form;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxii.common.dao.LanguageSettingDao;
import com.galaxii.common.entity.LanguageCode;
import com.galaxii.common.entity.LanguageSetting;
import com.opensymphony.xwork2.ActionSupport;

public abstract class AbstractForm
	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected transient final Logger logger = LoggerFactory.getLogger(getClass());

	@javax.annotation.Resource
	private transient LanguageSettingDao languageSettingDao;

	private transient String token;

	public Map<String, String> getEnumSelectList(Class<? extends Enum<?>> eclazz) {
		return getEnumSelectList(eclazz, false);
	}
	
	public Map<String, String> getEnumSelectList(Class<? extends Enum<?>> eclazz, boolean includeUndefined) {
		Map<String, String> ret = new LinkedHashMap<String, String>();
		for(Enum<?> e : eclazz.getEnumConstants()) {
			if(!includeUndefined && "UNDEFINED".equals(e.name())) {
				continue;
			}
			ret.put(e.name(), e.toString());
		}
		return ret;
	}
	
	public Map<String, String> getEnumSelectList(Class<? extends Enum<?>> eclazz, String code) {
		Map<String, String> codes = getSettingSelectList(code);
		if(codes == null) {
			return null;
		}
		// TODO 何か変
		Map<String, String> ret = new LinkedHashMap<String, String>();
		for(Map.Entry<String, String> c : codes.entrySet()) {
			for(Enum<?> e : eclazz.getEnumConstants()) {
				if(c.getKey().equals(e.name())) {
					ret.put(c.getKey(), c.getValue());
					continue;
				}
			}
		}
		return ret;
	}

	public Map<String, String> getSettingSelectList(final String settingCode) {
		LanguageSetting languageSetting = languageSettingDao.findByCode(settingCode);
		return languageSetting.getContents();
	}

	public Map<String, String> getLanguageSelectList() {
		return getEnumSelectList(LanguageCode.class);
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public boolean validate(ActionSupport as) {
		return true;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}

}
