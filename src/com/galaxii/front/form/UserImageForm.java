package com.galaxii.front.form;

import java.util.List;

import com.galaxii.common.dto.ImageMeta;
import com.opensymphony.xwork2.ActionSupport;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@Component
public class UserImageForm extends AbstractImageContentsForm {

	private static final long serialVersionUID = 1L;

	public boolean validate(ActionSupport as) {
		
		// community images
		List<ImageMeta> imageMetas = getImageMetas();
		if(imageMetas == null || 0 == imageMetas.size()) {
			as.addFieldError("userImageForm.images", as.getText("invalidate.required"));
		}

		return !as.hasFieldErrors();
	}
}
