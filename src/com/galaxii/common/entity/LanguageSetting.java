package com.galaxii.common.entity;

import java.util.Map;

import javax.persistence.Cacheable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Cacheable(true)
@Entity
@Table( name = "language_settings" )
public class LanguageSetting extends AbstractCompositeIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LanguageSettingId id;
	private Map<String, String> contents;

	@EmbeddedId
	public LanguageSettingId getId() {
		return id;
	}

	public void setId(LanguageSettingId id) {
		this.id = id;
	} 

	@Type(type = "com.galaxii.common.hibernate.type.JsonPropertyUserType")
	public Map<String, String> getContents() {
		return contents;
	}

	public void setContents(Map<String, String> contents) {
		this.contents = contents;
	}
}

