package com.galaxii.common.views.freemarker.template;

import java.util.List;

import com.galaxii.common.util.StringUtils;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class CamelCase2DashMethod implements TemplateMethodModel {
	
    public TemplateModel exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		if(args.isEmpty()) {
			throw new TemplateModelException("Wrong arguments");
		}
		String name = (String)args.get(0);
		
		return new SimpleScalar( StringUtils.dasherize(name) );
    }
    
}
