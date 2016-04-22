package com.galaxii.common.views.freemarker;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.apache.struts2.views.freemarker.ScopesHashModel;

import com.galaxii.common.views.freemarker.template.AssetsMethod;
import com.galaxii.common.views.freemarker.template.CamelCase2DashMethod;
import com.galaxii.common.views.freemarker.template.CommunityContentsCategoryMethod;
import com.galaxii.common.views.freemarker.template.CommunityImageMethod;
import com.galaxii.common.views.freemarker.template.CommunityImageSizeMethod;
import com.galaxii.common.views.freemarker.template.CommunityRecommendImageMethod;
import com.galaxii.common.views.freemarker.template.CommunityTopicImageMethod;
import com.galaxii.common.views.freemarker.template.CutStrMethod;
import com.galaxii.common.views.freemarker.template.EmotionMethod;
import com.galaxii.common.views.freemarker.template.FTimeMethod;
import com.galaxii.common.views.freemarker.template.LanguageSettingMethod;
import com.galaxii.common.views.freemarker.template.MaskPasswordMethod;
import com.galaxii.common.views.freemarker.template.PathInfoMethod;
import com.galaxii.common.views.freemarker.template.StripTagsMethod;
import com.galaxii.common.views.freemarker.template.UrlMethod;
import com.galaxii.common.views.freemarker.template.UserBgImageMethod;
import com.galaxii.common.views.freemarker.template.UserBgImageSizeMethod;
import com.galaxii.common.views.freemarker.template.UserImageMethod;
import com.galaxii.common.views.freemarker.template.UserImageSizeMethod;
import com.opensymphony.xwork2.util.ValueStack;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class CustomFreemarkerManager extends FreemarkerManager {

	@Override
	protected Configuration createConfiguration(ServletContext servletContext) 
		throws TemplateException {
		Configuration cfg = super.createConfiguration(servletContext);
		// TODO これつけないと何故かブラウザ毎にdate formatが異なる -> 調査
		cfg.setDateFormat("yyyy/MM/dd HH:mm:ss");
		cfg.setDateTimeFormat("yyyy/MM/dd HH:mm:ss");
		cfg.setNumberFormat("0.######");
		//cfg.setWhitespaceStripping(true);
/*
		//cfg.setDefaultEncoding("UTF-8");
		cfg.setSetting(Configuration.OUTPUT_ENCODING_KEY, "UTF-8");
		cfg.addAutoImport("my", "/WEB-INF/content/lib/my.ftl");
*/
		return cfg;
	}
/*
	@Override
	protected void loadSettings(ServletContext servletContext) {
		super.loadSettings(servletContext);
	}
*/	
	@Override
	public void populateContext(ScopesHashModel model, ValueStack stack, 
		Object action, HttpServletRequest request, HttpServletResponse response) {
		super.populateContext(model, stack, action, request, response);
		model.put("assets", new AssetsMethod());
		model.put("url", new UrlMethod());
		model.put("pathinfo", new PathInfoMethod());
		model.put("stripTags", new StripTagsMethod());
		model.put("cutStr", new CutStrMethod());
		model.put("emotion", new EmotionMethod());
		model.put("maskPassword", new MaskPasswordMethod());
		model.put("languageSetting", new LanguageSettingMethod());
		model.put("fTime", new FTimeMethod());
		model.put("communityImageSrc", new CommunityImageMethod());
		model.put("communityTopicImageSrc", new CommunityTopicImageMethod());
		model.put("communityRecommendImageSrc", new CommunityRecommendImageMethod());
		model.put("camelCase2dash", new CamelCase2DashMethod());
		model.put("communityImageSize", new CommunityImageSizeMethod());
		model.put("communityContentsCategory", new CommunityContentsCategoryMethod());
		model.put("userImageSrc", new UserImageMethod());
		model.put("userImageSize", new UserImageSizeMethod());
		model.put("userBgImageSrc", new UserBgImageMethod());
		model.put("userBgImageSize", new UserBgImageSizeMethod());
	}
}
