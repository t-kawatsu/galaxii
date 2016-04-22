package com.galaxii.common.views.freemarker.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.galaxii.common.dao.LanguageSettingDao;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class LanguageSettingMethod implements TemplateMethodModel {

	private LanguageSettingDao languageSettingDao;
	private Map<String, Map<String, String>> codeMap = 
			new HashMap<String, Map<String, String>>();

    public TemplateModel exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		if(args.size() < 2) {
			throw new TemplateModelException("Wrong arguments");
		}
		String code = (String)args.get(0);
		String key = (String)args.get(1);
		if(StringUtils.isEmpty(key)) {
			return null;
		}
		
		if(!codeMap.containsKey(code)) {
			if(args.size() == 2) {
				codeMap.put(code, getLanguageSettingDao().findByCode(code).getContents());
			} else {
				codeMap.put(code, getLanguageSettingDao().findByCode(code).getContents());
			}
		} 
		return new SimpleScalar( codeMap.get(code).get(key) );
    }

	public LanguageSettingDao getLanguageSettingDao() {
		ApplicationContext context =
				WebApplicationContextUtils.getRequiredWebApplicationContext(
	                                    ServletActionContext.getServletContext());
		if(languageSettingDao == null) {
			languageSettingDao = (LanguageSettingDao)context.getBean("languageSettingDao");
		}
		return languageSettingDao;
	}
}
