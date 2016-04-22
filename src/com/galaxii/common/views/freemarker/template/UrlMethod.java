package com.galaxii.common.views.freemarker.template;

import java.util.List;

import com.galaxii.common.util.UrlHelper;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class UrlMethod implements TemplateMethodModel {

	private UrlHelper urlHelper = new UrlHelper();

    public TemplateModel exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		String url = null;
		switch(args.size()) {
			case 0:
				url = urlHelper.buildUrl();
				break;
			case 1:
				url = urlHelper.buildUrl(args.get(0).toString());
				break;
			case 2:
				url = urlHelper.buildUrl(args.get(0).toString(), null,	
					Boolean.parseBoolean( args.get(1).toString() ));
				break;
			case 3:
				url = urlHelper.buildUrl(args.get(0).toString(), null, 
					Boolean.parseBoolean( args.get(1).toString() ), 
					Boolean.parseBoolean( args.get(2).toString() ));
				break;
			case 4:
				url = urlHelper.buildUrl(args.get(0).toString(), null, 
					Boolean.parseBoolean( args.get(1).toString() ), 
					Boolean.parseBoolean( args.get(2).toString() ),
					args.get(3).toString() );
		}
		return new SimpleScalar(url);
    }
}
