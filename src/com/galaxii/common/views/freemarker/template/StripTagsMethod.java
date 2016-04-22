package com.galaxii.common.views.freemarker.template;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class StripTagsMethod implements TemplateMethodModel {

	/**
	 * @see http://jsoup.org/apidocs/org/jsoup/safety/Whitelist.html
	 */
    public TemplateModel exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		if(args.size() < 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		Whitelist whitelist = Whitelist.basicWithImages();
		if(2 <= args.size()) {
			String allowedTags = (String)args.get(1);
			whitelist.addTags(allowedTags.split(","));
		}
		return new SimpleScalar( Jsoup.clean((String)args.get(0), whitelist) );
    }
}
