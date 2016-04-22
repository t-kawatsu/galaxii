package com.galaxii.front.form;

import java.io.File;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class ImageForm extends AbstractForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File file;
	private String fileContentType; //The content type of the file
	private String fileFileName; //The uploaded file name
	
	private static final String[] ALLOW_CONTENT_TYPES = 
		{ "image/jpeg", "image/jpg", "image/png", "image/x-png", "image/gif" };
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public boolean validate(ActionSupport as) {
		if(null == getFile()) {
			as.addFieldError("imageForm.file", as.getText("invalidate.fileUpload"));
		}
		if(!ArrayUtils.contains(ALLOW_CONTENT_TYPES, getFileContentType())) {
			as.addFieldError("imageForm.fileContentType", as.getText("invalidate.fileContentType"));
		}
		return !as.hasFieldErrors();
	}
}
