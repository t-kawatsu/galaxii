package com.galaxii.front.action.user;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.CommunityActivity;
import com.galaxii.common.entity.CommunityUser;
import com.galaxii.common.service.CommunityActivityService;
import com.galaxii.common.service.CommunityService;
import com.galaxii.common.util.SimplePager;

public class ReadAction extends AbstractReadAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int DISP_ACTIVITY_NUM_PAR_PAGE = 10;
	private static final int DISP_USER_COMMUNITY_NUM_PAR_PAGE = 15;
	
	@Resource
	private CommunityActivityService communityActivityService;
	@Resource
	private CommunityService communityService;
	
	private Integer page;
	
	private SimplePager<CommunityActivity> communityActivities;
	private SimplePager<CommunityUser> communityUsers;
	
    @Action(value="user/mypage",
            results={
                @Result(name="success", location="/user/read/${id}", type="redirect")
            }   
    )
    public String mypageAction() throws Exception {
    	if(!getIsLogined()) {
    		return LOGIN;
    	}
    	id = getCurrentUser().getId();
    	return SUCCESS;
    }
    
    @Actions({
      @Action(value="user/read/{id}/{contents}",
        results={
    		@Result(name="success", location="user/read.ftl"),
    		@Result(name="pjax", location="user/_u-sub-contents.ftl")
        }   
      ),
      @Action(value="user/read/{id}",
    	results={
    		@Result(name="success", location="user/read.ftl"),
    		@Result(name="pjax", location="user/_u-sub-contents.ftl")
    	}
      )
    })
    @Override
    public String execute() throws Exception {
    	String ret = beforeReadProcess(id);
    	if(!SUCCESS.equals(ret)) {
    		return ret;
    	}
    	switch(getContents()) {
  	  	  case WATCH:
  	  		  communityActivities = communityActivityService.factoryWatchUserActivityListSessionPaginator(
    			sessUtil, user.getId(), DISP_ACTIVITY_NUM_PAR_PAGE).clearPage().paginate();
		  break;
    	  case ACTIVITY:
    		  communityActivities = communityActivityService.factoryUserListSessionPaginator(
        			sessUtil, user.getId(), DISP_ACTIVITY_NUM_PAR_PAGE).clearPage().paginate();
    		  break;
    	  case COMMUNITY:
    		  communityUsers = communityService.paginateUserCommunities(
    				  user.getId(), DISP_USER_COMMUNITY_NUM_PAR_PAGE, page);
    		  break;
    	  case DETAIL:
    	  default: 
    	}
    	if(isPjax()) {
    		return PJAX;
    	}
        return SUCCESS;
    }
	
    @Action(value="user/read-ajax/{id}",
            results={
                @Result(name="success", location="user/read-ajax.ftl")
            }   
    )
    public String readAjaxAction() throws Exception {
    	user = userDao.findById(id);
		if(getIsLogined()) {
			isUserWatched = userService.isWatchUser(
    				getCurrentUser().getId(), user.getId());
		}
        return SUCCESS;
    }
    
	public void setPage(Integer page) {
		this.page = page;
	}
	
    public SimplePager<CommunityActivity> getCommunityActivities() {
    	return communityActivities;
    }
    
    public SimplePager<CommunityUser> getCommunityUsers() {
    	return this.communityUsers;
    }
}
