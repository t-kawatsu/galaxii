package com.galaxii.front.action.community_image;

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dto.AmazonItem;
import com.galaxii.common.entity.ImageType;
import com.galaxii.common.entity.User;
import com.galaxii.common.service.CommunityImageService;
import com.galaxii.common.service.amazon.AmazonProductAdvertisingClient;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.AmazonSearchUrlForm;
import com.galaxii.front.form.AmazonSearchWordForm;

public class CreateAmazonAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private AmazonSearchUrlForm amazonSearchUrlForm;
	@Resource
	private AmazonSearchWordForm amazonSearchWordForm;
	
	@Resource
	private AmazonProductAdvertisingClient amazonProductAdvertisingClient;
	@Resource
	private CommunityImageService communityImageService;
	
	private List<AmazonItem> amazonItems;
	
	private Map<String,Object> uploadResultJson;
	private String asin;
	
	@Override
    public String execute() throws Exception {
		return SUCCESS;
    }
	
	@Action(value="community-image/amazon-search-url-ajax",
			results={
			@Result(name="input", location="community-image/amazon-create.ftl"),
            @Result(name="success", location="community-image/amazon-create.ftl")
		}
	)
	public String searchUrlAjaxAction() throws Exception {
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!amazonSearchUrlForm.validate(this)) {
            return INPUT;
        }
		return SUCCESS;
	}

	
	@Action(value="community-image/amazon-search-word-ajax",
			results={
			@Result(name="input", location="community-image/amazon-create.ftl"),
            @Result(name="success", location="community-image/amazon-create.ftl")
		}
	)
	public String searchWordAjaxAction() throws Exception {
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!amazonSearchWordForm.validate(this)) {
            return INPUT;
        }
        // detect amazon items
        amazonItems = amazonProductAdvertisingClient.itemSearch(
        		amazonSearchWordForm.getWord());
		return SUCCESS;
	}
	
	@Action(value="community-image/amazon-upload-ajax",
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
		if(StringUtils.isEmpty(asin)) {
			return ERROR;
		}
		AmazonItem amazonItem = 
			amazonProductAdvertisingClient.findByAsin(asin);
        if(amazonItem == null || amazonItem.getMediumImage() == null) {
        	return ERROR;
        }
        User user = getCurrentUser();
        String fileId = communityImageService.storeTmpImageFromUrl(
        		new URL(amazonItem.getLargeImage().getUrl()), user.getId());
        uploadResultJson = communityImageService
        		.formatTmpCreatedData(fileId, ImageType.AMAZON, asin);
		return SUCCESS;
	}

	public AmazonSearchUrlForm getAmazonSearchUrlForm() {
		return amazonSearchUrlForm;
	}

	public void setAmazonSearchUrlForm(AmazonSearchUrlForm amazonSearchUrlForm) {
		this.amazonSearchUrlForm = amazonSearchUrlForm;
	}

	public AmazonSearchWordForm getAmazonSearchWordForm() {
		return amazonSearchWordForm;
	}

	public void setAmazonSearchWordForm(AmazonSearchWordForm amazonSearchWordForm) {
		this.amazonSearchWordForm = amazonSearchWordForm;
	}
	
	public List<AmazonItem> getAmazonItems() {
		return amazonItems;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}
	
	public Map<String,Object> getUploadResultJson() {
		return uploadResultJson;
	}
}