package com.galaxii.common.views.freemarker.template;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class MaskPasswordMethod implements TemplateMethodModel {

	private final String defMask = "*";

    public TemplateModel exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		if(args.size() < 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		String password = (String)args.get(0);
		String mask = 2 <= args.size() ? (String) args.get(1) : defMask;
		return new SimpleScalar( StringUtils.repeat(mask, password.length()) );
    }
}
