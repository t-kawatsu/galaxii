package com.galaxii.common.views.freemarker.template;

import java.util.List;

import com.galaxii.common.entity.CommunityContentsCategory;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class CommunityContentsCategoryMethod implements TemplateMethodModel {

    public TemplateModel exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		if(args.size() < 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		String name = (String)args.get(0);
		CommunityContentsCategory ccc = CommunityContentsCategory.nameOf(name);
		
		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
		return wrapper.wrap(ccc);
    }
}

