package com.galaxii.front.action.community_image;

import java.io.File;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.CommunityImageSize;
import com.galaxii.common.entity.User;
import com.galaxii.common.service.CommunityImageService;
import com.galaxii.front.action.AbstractAction;

public class ReadTmpAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private CommunityImageService communityImageService;
	
	private String fileId;
	private String fileSize;
	private InputStream inputStream;

	@Action(value="community-image/read-tmp/{fileId}/{fileSize}",
		results={
			@Result(name="success",  type="stream", params={
					"contentType", "image/jpeg"
			})
		}
	)
	@Override
	public String execute() throws Exception {
		User user = getCurrentUser();
		CommunityImageSize is = CommunityImageSize.nameOf(fileSize);
		File file = communityImageService.getTmpImageFile(fileId, is, user.getId());
		inputStream = FileUtils.openInputStream(file);
		return SUCCESS;
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