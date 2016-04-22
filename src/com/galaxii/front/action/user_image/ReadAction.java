package com.galaxii.front.action.user_image;

import java.io.File;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserImageSize;
import com.galaxii.common.service.UserImageService;
import com.galaxii.front.action.AbstractAction;

public class ReadAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private UserImageService userImageService;
	
	private String fileId;
	private String fileSize;
	private InputStream inputStream;

	@Action(value="user-image/read-tmp/{fileId}/{fileSize}",
		results={
			@Result(name="success",  type="stream", params={
					"contentType", "image/jpeg"
			})
		}
	)
	@Override
	public String execute() throws Exception {
		User user = getCurrentUser();
		UserImageSize is = UserImageSize.nameOf(fileSize);
		File file = userImageService.getTmpImageFile(fileId, is, user.getId());
		inputStream = FileUtils.openInputStream(file);
		return SUCCESS;
	}
	
	@Action(value="user-image/read-ajax/{userId}",
		results = { @Result(name = "success", location = "user-image/read.ftl") })
	public String readAction() throws Exception {
		return NONE;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
}