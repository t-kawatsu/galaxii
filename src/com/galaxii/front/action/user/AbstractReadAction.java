package com.galaxii.front.action.user;

import javax.annotation.Resource;

import com.galaxii.common.dao.UserDao;
import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserContentsCategory;
import com.galaxii.common.entity.UserInformation;
import com.galaxii.common.entity.UserWatch;
import com.galaxii.common.service.UserService;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.action.AbstractAction;

public abstract class AbstractReadAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	protected static final int DISP_USER_WATCHE_NUM = 15;
	protected static final int DISP_USER_INFORMATION_NUM = 7;
	
	protected UserContentsCategory userContentsCategory
		= UserContentsCategory.ACTIVITY;
	
	@Resource
	protected UserService userService;
	@Resource
	protected UserDao userDao;
	
	protected Integer id;
	protected User user;
	protected boolean isUserWatched;
	protected boolean isMyPage;
	
	private SimplePager<UserWatch> userWatches;
	private SimplePager<UserWatch> userWatchedes;
	private SimplePager<UserInformation> userInformations;
	
    protected String beforeReadProcess(Integer id) throws Exception {
    	if(id == null) {
    		return ERROR;
    	}
    	if(getIsLogined() && getCurrentUser().getId().equals(id)) {
    		// mypage
    		user = getCurrentUser();
    		isMyPage = true;
    	} else {
    		user = userDao.findById(id);
    		if(user == null) {
    			return ERROR;
    		}
    		if(getIsLogined()) {
    			isUserWatched = userService.isWatchUser(
        				getCurrentUser().getId(), user.getId());
    		}
    	}
    	
    	if(isPjax()) {
    		return SUCCESS;
    	}
    	
    	userWatches = userService.paginateUserWatches(
    			user.getId(), DISP_USER_WATCHE_NUM, 1);
    	userWatchedes = userService.paginateUserWatchedes(
    			user.getId(), DISP_USER_WATCHE_NUM, 1);
    	if(isMyPage) {
    		/*
    		userInformations = userInformationService
    			.factorySessionPaginator(sessUtil, user.getId(), 
    					DISP_USER_INFORMATION_NUM)
    			.clearPage().paginate();
    		*/
    	}
    	return SUCCESS;
    }
    
    public Integer getId() {
    	return id;
    }
    
    public void setId(Integer id) {
    	this.id = id;
    }
    
	public void setContents(String contents) {
		userContentsCategory = UserContentsCategory.nameOf(contents);
	}
	
	public UserContentsCategory getContents() {
		return userContentsCategory;
	}
	
    public User getUser() {
    	return user;
    }
    
    public SimplePager<UserWatch> getUserWatches() {
    	return userWatches;
    }
    
    public SimplePager<UserWatch> getUserWatchedes() {
    	return userWatchedes;
    }
    
    public SimplePager<UserInformation> getUserInformations() {
    	return userInformations;
    }
    
	public boolean getIsUserWatched() {
		return isUserWatched;
	}
	
	public boolean getIsMyPage() {
		return isMyPage;
	}
}
