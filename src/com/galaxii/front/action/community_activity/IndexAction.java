package com.galaxii.front.action.community_activity;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.UserDao;
import com.galaxii.common.entity.CommunityActivity;
import com.galaxii.common.entity.User;
import com.galaxii.common.service.CommunityActivityService;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.action.AbstractAction;

public class IndexAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int DISP_ACTIVITY_NUM_PAR_PAGE = 15;
	
	@Resource
	private CommunityActivityService communityActivityService;
	@Resource
	private UserDao userDao;

	private Integer id;
	private User user;
	private SimplePager<CommunityActivity> communityActivities;

	@Action(value="community-activity/more-community-ajax",
		results={
            @Result(name="success", location="community-activity/_community-activities.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		// community id
		communityActivities = communityActivityService.factorySessionPaginator(
				sessUtil, id, DISP_ACTIVITY_NUM_PAR_PAGE).paginate();
		return SUCCESS;
    }
	
	@Action(value="community-activity/more-user-ajax",
			results={
	            @Result(name="success", location="community-activity/_user-activities.ftl")
			}
	)
    public String moreUserAjaxAction() throws Exception {
		// user id
		user = userDao.findById(id);
		communityActivities = communityActivityService.factoryUserListSessionPaginator(
      			sessUtil, id, DISP_ACTIVITY_NUM_PAR_PAGE).paginate();
		return SUCCESS;
    }
	
	@Action(value="community-activity/more-watch-ajax",
			results={
	            @Result(name="success", location="community-activity/_activities.ftl")
			}
	)
    public String moreWatchAjaxAction() throws Exception {
		if(!getIsLogined()) {
			return NONE;
		}
		communityActivities = communityActivityService.factoryWatchUserActivityListSessionPaginator(
      			sessUtil, getCurrentUser().getId(), DISP_ACTIVITY_NUM_PAR_PAGE).paginate();
		return SUCCESS;
    }
	
	@Action(value="community-activity/more-ajax",
			results={
	            @Result(name="success", location="community-activity/_activities.ftl")
			}
	)
    public String moreAjaxAction() throws Exception {
		// user id
		user = userDao.findById(id);
		communityActivities = communityActivityService.factoryGlobalListSessionPaginator(
      			sessUtil, DISP_ACTIVITY_NUM_PAR_PAGE).paginate();
		return SUCCESS;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public SimplePager<CommunityActivity> getCommunityActivities() {
		return communityActivities;
	}
}