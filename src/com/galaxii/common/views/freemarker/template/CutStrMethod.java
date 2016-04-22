package com.galaxii.common.views.freemarker.template;

import java.util.List;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class CutStrMethod implements TemplateMethodModel {

	private String tail = "…";

    public TemplateModel exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		if(args.size() < 2) {
			throw new TemplateModelException("Wrong arguments");
		}
		String str = (String)args.get(0);
		int len = Integer.parseInt((String)args.get(1));
		if(str.length() <= len) {
			return new SimpleScalar(str);
		}

		return new SimpleScalar( str.substring(0, len - tail.length() ) + tail );
    }
}
