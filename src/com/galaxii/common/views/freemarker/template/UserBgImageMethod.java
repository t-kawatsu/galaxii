package com.galaxii.common.views.freemarker.template;

import java.util.Arrays;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.galaxii.common.service.AbstractUserImageService;
import com.galaxii.common.service.UserBgImageService;

import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class UserBgImageMethod extends AssetsMethod {
	
	private AbstractUserImageService userImageService;

    public TemplateModel exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
    	String dir = getImageService().getImageSubDir(
    			Integer.parseInt((String)args.get(0)), Integer.parseInt((String)args.get(1)));
    	String size = (String)args.get(2);
    	return super.exec(Arrays.asList(dir + "/" + size + ".jpg", "images", "user"));
    }

	private AbstractUserImageService getImageService() {
		ApplicationContext context =
				WebApplicationContextUtils.getRequiredWebApplicationContext(
	                                    ServletActionContext.getServletContext());
		if(userImageService == null) {
			userImageService = (UserBgImageService)context.getBean("userBgImageService");
		}
		return userImageService;
	}
}
