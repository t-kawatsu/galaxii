package com.galaxii.common.views.freemarker.template;

import java.util.List;

import com.galaxii.common.entity.CommunityImageSize;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class CommunityImageSizeMethod implements TemplateMethodModel {

    public TemplateModel exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		if(args.size() < 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		String name = (String)args.get(0);
		CommunityImageSize cis = CommunityImageSize.nameOf(name);
		
		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
		return wrapper.wrap(cis);
    }
}

