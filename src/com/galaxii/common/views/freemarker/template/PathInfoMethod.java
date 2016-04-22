package com.galaxii.common.views.freemarker.template;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class PathInfoMethod implements TemplateMethodModel {

    public TemplateModel exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		if(args.size() < 1 || StringUtils.isEmpty((String)args.get(0))) {
			throw new TemplateModelException("Wrong arguments");
		}
		String name = ServletActionContext.getActionMapping().getName();
		String names[] = StringUtils.split(name, '/');
		String type = (String) args.get(0);
		String ret = null;
		if("controller".equals(type)) {
			ret = 1 <= names.length ? names[0] : "index";
		} else if("action".equals(type)) {
			ret = 2 <= names.length ? names[1] : "index";
		}
		return new SimpleScalar(ret);
    }
}
