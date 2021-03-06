package com.galaxii.common.views.freemarker.template;

import java.util.Arrays;
import java.util.List;

import com.galaxii.common.service.CommunityImageService;

import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class CommunityImageMethod extends AssetsMethod {

    public TemplateModel exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
    	String dir = CommunityImageService.getImageSubDir(
    			Integer.parseInt((String)args.get(0)), Integer.parseInt((String)args.get(1)));
    	String size = (String)args.get(2);
    	return super.exec(Arrays.asList(dir + "/" + size + ".jpg", "images", "user"));
    }
}
