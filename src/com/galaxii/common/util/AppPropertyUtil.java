package com.galaxii.common.util;

import com.opensymphony.xwork2.DefaultTextProvider;
import com.opensymphony.xwork2.TextProvider;

public class AppPropertyUtil {
	
	private static AppPropertyUtil instance = new AppPropertyUtil();
	private static TextProvider tp = new DefaultTextProvider();

	private AppPropertyUtil() {}

	public static AppPropertyUtil getInstance() {
		return instance;
	}

	private String getText(final String name) {
		return getText(name, null);
	}

	private String getText(final String name, final String def) {
		return tp.getText(name);
	}

	public String getAppSiteDomain() {
		return getText("app.site.domain");
	}

	public String getAppSiteSslPort() {
		return getText("app.site.sslPort", "443");
	}

	public String getAppSitePort() {
		return getText("app.site.port", "80");
	}

	public Boolean getAppUseSsl() {
		return Boolean.valueOf( getText("app.useSSL") );
	}

	public String getMailSmtpHost() {
		return getText("mail.smtp.host");
	}

	public Integer getMailSmtpPort() {
		return Integer.parseInt( getText("mail.smtp.port") );
	}

	public String getMailSmtpUsername() {
		return getText("mail.smtp.username");
	}

	public String getMailSmtpPassword() {
		return getText("mail.smtp.password");
	}

	public Integer getAppTagRegistLimit() {
		return Integer.parseInt( getText("app.tag.regist.limit") );
	}
}
