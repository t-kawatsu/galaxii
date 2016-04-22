package com.galaxii.front.action.community_image;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.CommunityActivityDao;
import com.galaxii.common.dao.CommunityImageDao;
import com.galaxii.common.entity.CommunityContentsCategory;
import com.galaxii.common.entity.CommunityImage;
import com.galaxii.common.service.CommunityImageService;
import com.galaxii.front.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private CommunityImageService communityImageService;
	@Resource
	private CommunityImageDao communityImageDao;
	@Resource
	private CommunityActivityDao communityAvtivityDao;
	
	private Integer id;
	private CommunityImage communityImage;

	@Action(value="community-image/delete-ajax/{id}",
		results={
			 @Result(name="success", 
					 location="community-image/delete-complete.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		if(!getIsLogined()) {
			return LOGIN;
		}
		communityImage = communityImageDao.findById(id);
        if(communityImage == null 
        	|| !communityImage.getUserId().equals(getCurrentUser().getId())) {
            return ERROR;
        }
        communityImageService.delete(communityImage);
        
        communityAvtivityDao.deleteActivity(
        		CommunityContentsCategory.IMAGE, id);
        userInformationService
        	.deleteCommunityImage(communityImage.getCommunityId(), getCurrentUser());
		return SUCCESS;
    }

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public CommunityImage getCommunityImage() {
		return communityImage;
	}
}