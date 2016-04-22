package com.galaxii.common.entity;

import javax.persistence.Cacheable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Cacheable(true)
@Entity
@Table( name = "language_mails" )
public class LanguageMail extends AbstractCompositeIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LanguageMailId id;
	private String subject;
	private String body;

	@EmbeddedId
	public LanguageMailId getId() {
		return id;
	}

	public void setId(LanguageMailId id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
