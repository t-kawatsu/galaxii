package com.galaxii.front.action.community_recommend;

import java.net.URL;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityDao;
import com.galaxii.common.dao.CommunityUserDao;
import com.galaxii.common.dto.SiteData;
import com.galaxii.common.entity.Community;
import com.galaxii.common.entity.CommunityRecommend;
import com.galaxii.common.service.CommunityActivityService;
import com.galaxii.common.service.CommunityRecommendService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.CommunityRecommendWebForm;

public class WebCreateAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityRecommendService communityRecommendSerice;
	@Resource
	private CommunityUserDao communityUserDao;
	@Resource
	private CommunityDao communityDao;
	@Resource
	private CommunityRecommendWebForm communityRecommendWebForm;
	@Resource
	private CommunityActivityService communityActivityService;
	
	private Integer communityId;
	private Community community;
	private SiteData siteData;

	@Action(value="community-recommend/web-create-ajax/{communityId}",
		results={
			@Result(name="input", location="community-recommend/_web-search-url.ftl"),
            @Result(name="success", location="community-recommend/create-complete.ftl")
		}
	)
	@Override
	public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
		community = communityDao.findById(communityId);
		if(community == null) {
			return ERROR;
		}
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }
        
        if(!communityRecommendWebForm.validate(this)) {
            return INPUT;
        }
        
        if(!communityUserDao.isExistCommunityUser(
        		communityId, getCurrentUser().getId())) {
        	return INPUT;
        }
        
        CommunityRecommend communityRecommend = null;
        try {
        	communityRecommend = communityRecommendSerice.create(
        			communityId, communityRecommendWebForm, getCurrentUser().getId());
        } catch(Exception e) {
        	return ERROR;
        }
        // activity
        communityActivityService
        	.insertCommunityRecommendActivity(communityRecommend);
        // user info
        userInformationService.insertCommunityRecommend(communityId, getCurrentUser());
        return SUCCESS;
	}
	
	@Action(value="community-recommend/web-search-url-ajax/{communityId}",
		results={
			@Result(name="input", location="community-recommend/_web-search-url.ftl"),
            @Result(name="success", location="community-recommend/_web-search-url.ftl")
		}
	)
	public String searchUrlAjaxAction() throws Exception {
        if(StringUtils.isEmpty(communityRecommendWebForm.getUrl())) {
            return INPUT;
        }
        if(!communityRecommendWebForm.validate(this)) {
            return INPUT;
        }
        // detect 
        try {
        	siteData = communityRecommendSerice.detectSiteData(
        		new URL(communityRecommendWebForm.getUrl()));
        } catch(Exception e) {
        	addFieldError("communityRecommendWebForm", getText("invalidate.htmlParse"));
        	return INPUT;
        }
        
		return SUCCESS;
	}
	
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	
	public Integer getCommunityId() {
		return communityId;
	}
	
	public void setCommunityRecommendWebForm(CommunityRecommendWebForm communityRecommendWebForm) {
		this.communityRecommendWebForm = communityRecommendWebForm;
	}
	
	public CommunityRecommendWebForm getCommunityRecommendWebForm() {
		return communityRecommendWebForm;
	}
	
	public SiteData getSiteData() {
		return siteData;
	}
}
