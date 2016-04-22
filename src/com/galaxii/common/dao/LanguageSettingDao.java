package com.galaxii.common.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.LanguageSetting;
import com.galaxii.common.entity.LanguageSettingId;

@Transactional
@Repository
public class LanguageSettingDao extends AbstractDao<LanguageSetting> {

	@Cacheable( value = "setting", key = "#code")
	public LanguageSetting findByCode(final String code) {
		Locale languageCode = new Locale("ja");
		return findByCode(code, languageCode);
	}

	public LanguageSetting findByCode(final String code, final Locale languageCode) {
		if(code == null) {
			return null;
		}
		return (LanguageSetting) getSession().createQuery(
			"FROM LanguageSetting ls WHERE ls.id.code = (:code) AND ls.id.languageCode = (:languageCode)")
		.setLocale("languageCode", languageCode)
		.setString("code", code)
		.uniqueResult();
	}

	public String getContentsValue(final String code, final String key) {
		Locale languageCode = new Locale("ja");
		return getContentsValue(code, key, languageCode);
	}

	public String getContentsValue(final String code, final String key, final Locale languageCode) {
		Map<String, String> contents = findByCode(code, languageCode).getContents();
		return contents.get(key);
	}
	
	public void save(LanguageSettingId id, String contents) throws IOException {
		Map<String, String> mappedContents = stringToMapContents(contents);
		LanguageSetting ls = new LanguageSetting();
		ls.setId(id);
		ls.setContents(mappedContents);
		save(ls);
	}
	
	public void update(LanguageSettingId id, String contents) throws IOException {
		Map<String, String> mappedContents = stringToMapContents(contents);
		LanguageSetting ls = findById(id);
		ls.setContents(mappedContents);
		update(ls);
	}
	
	public String mapToStringContents(Map<String, String> maps) {
		if(maps == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for(Map.Entry<String, String> e : maps.entrySet()) {
			sb.append(e.getKey() + ":" + e.getValue() + System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	
	private Map<String, String> stringToMapContents(String string) throws IOException {
	    BufferedReader reader = new BufferedReader(new StringReader(string));
	    Map<String, String> map = new LinkedHashMap<String, String>();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        int index = line.indexOf(":");
	        if (index == -1) {
	            throw new IllegalArgumentException();
	        }
	        String key = line.substring(0, index);
	        String value = line.substring(index + 1);
	        map.put(key, value);
	    }
	    return map;
	}
}
