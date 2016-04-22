package com.galaxii.common.dao;

import java.util.Locale;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.LanguageMail;

@Repository
@Transactional
public class LanguageMailDao extends AbstractDao<LanguageMail> {

	public LanguageMail findByCode(final String code) {
		return findByCode(code, new Locale("ja"));
	}

	public LanguageMail findByCode(final String code, final Locale locale) {
		if(code == null || locale == null) {
			return null;
		}
		return (LanguageMail) getSession().createQuery(
			"FROM LanguageMail lm WHERE lm.id.code = :code AND lm.id.languageCode = :languageCode")
		.setLocale("languageCode", locale)
		.setString("code", code)
		.uniqueResult();
	}
}
