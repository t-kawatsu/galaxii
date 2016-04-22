package com.galaxii.front.action.community_image;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dto.YoutubeItem;
import com.galaxii.common.entity.ImageType;
import com.galaxii.common.entity.User;
import com.galaxii.common.service.CommunityImageService;
import com.galaxii.common.service.YoutubeClientService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.YoutubeSearchUrlForm;
import com.galaxii.front.form.YoutubeSearchWordForm;

public class CreateYoutubeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private YoutubeSearchUrlForm youtubeSearchUrlForm;
	@Resource
	private YoutubeSearchWordForm youtubeSearchWordForm;

	@Resource
	private CommunityImageService communityImageService;
	@Resource
	private YoutubeClientService youtubeClientService;
	
	private List<YoutubeItem> youtubeItems;
	
	private Map<String,Object> uploadResultJson;
	private String entryId;
	
	@Action(value="community-image/youtube-search-url-ajax",
			results={
			@Result(name="input", location="community-image/youtube-create.ftl"),
            @Result(name="success", location="community-image/youtube-create.ftl")
		}
	)
	public String searchUrlAjaxAction() throws Exception {
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!youtubeSearchUrlForm.validate(this)) {
            return INPUT;
        }
        YoutubeItem youtubeItem = youtubeClientService.findVideoEntryByUrl(
        		youtubeSearchUrlForm.getUrl());
        if(youtubeItem != null) {
        	youtubeItems = new ArrayList<YoutubeItem>();
        	youtubeItems.add(youtubeItem);
        }
        return SUCCESS;
	}

	
	@Action(value="community-image/youtube-search-word-ajax",
			results={
			@Result(name="input", location="community-image/youtube-create.ftl"),
            @Result(name="success", location="community-image/youtube-create.ftl")
		}
	)
	public String searchWordAjaxAction() throws Exception {
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!youtubeSearchWordForm.validate(this)) {
            return INPUT;
        }
        youtubeItems = youtubeClientService.search(
        		youtubeSearchWordForm.getWord());
		return SUCCESS;
	}
	
	@Action(value="community-image/youtube-upload-ajax",
			results={
				@Result(name="success", type="json", params={
						"statusCode", "200",
						"contentType", "text/html",
						"noCache", "true",
						"root", "uploadResultJson"
						}),
				@Result(name="login", type="json", params={
						"statusCode", "401",
						"contentType", "text/html",
						"noCache", "true",
						"root", "uploadResultJson"
						}),
				@Result(name="input", type="json", params={
						"statusCode", "200",
						"contentType", "text/html",
						"noCache", "true",
						"root", "uploadResultJson"
						})
			}
	)
	public String uploadAjaxAction() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
		if(StringUtils.isEmpty(entryId)) {
			return ERROR;
		}
		YoutubeItem youtubeItem = youtubeClientService
				.findVideoEntryById(entryId);
        if(youtubeItem == null || youtubeItem.getImage() == null) {
        	return ERROR;
        }
        User user = getCurrentUser();
        String fileId = communityImageService.storeTmpImageFromUrl(
        		new URL(youtubeItem.getImage().getUrl()), user.getId());
        uploadResultJson = communityImageService
        		.formatTmpCreatedData(fileId, ImageType.YOUTUBE, entryId);
		return SUCCESS;
	}
	
	public Map<String,Object> getUploadResultJson() {
		return uploadResultJson;
	}
	
	public List<YoutubeItem> getYoutubeItems() {
		return youtubeItems;
	}

	public YoutubeSearchUrlForm getYoutubeSearchUrlForm() {
		return youtubeSearchUrlForm;
	}

	public void setYoutubeSearchUrlForm(YoutubeSearchUrlForm youtubeSearchUrlForm) {
		this.youtubeSearchUrlForm = youtubeSearchUrlForm;
	}

	public YoutubeSearchWordForm getYoutubeSearchWordForm() {
		return youtubeSearchWordForm;
	}

	public void setYoutubeSearchWordForm(YoutubeSearchWordForm youtubeSearchWordForm) {
		this.youtubeSearchWordForm = youtubeSearchWordForm;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
}