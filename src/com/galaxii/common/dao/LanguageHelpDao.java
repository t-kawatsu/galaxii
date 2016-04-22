package com.galaxii.common.dao;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.LanguageHelp;

@Repository
@Transactional
public class LanguageHelpDao extends AbstractDao<LanguageHelp> {

	public LanguageHelp findById(final Integer id) {
		Locale languageCode = new Locale("ja");
		return findById(id, languageCode);
	}

	public LanguageHelp findById(final Integer id, final Locale languageCode) {
		if(id == null) {
			return null;
		}
		return (LanguageHelp) getSession().createQuery(
			"FROM LanguageHelp lh WHERE lh.id = (:id) AND lh.languageCode = (:languageCode)")
		.setInteger("id", id)
		.setLocale("languageCode", languageCode)
		.uniqueResult();
	}

	public List<LanguageHelp> findAll() {
		Locale languageCode = new Locale("ja");
		return resultList( getSession().createQuery(
			"FROM LanguageHelp lh WHERE lh.languageCode = (:languageCode)")
		.setLocale("languageCode", languageCode) );
	}
}
