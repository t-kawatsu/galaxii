package com.galaxii.front.action.user_watch;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.UserDao;
import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserWatch;
import com.galaxii.common.service.UserService;
import com.galaxii.common.util.SimplePager;
import com.galaxii.front.action.AbstractAction;

public class IndexAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int DISP_USER_WATCHE_NUM = 15;
	
	@Resource
	private UserService userService;
	@Resource
	private UserDao userDao;
	
	private Integer userId;
	private User user;
	private Integer page;
	private SimplePager<UserWatch> userWatches;
	
	@Action(value="user-watch/index-watch/{userId}",
		results={
            @Result(name="success", location="user-watch/index-watch.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		user = userDao.findById(userId);
		if(user == null) {
			return ERROR;
		}
    	userWatches = userService.paginateUserWatches(
    			user.getId(), DISP_USER_WATCHE_NUM, page);
		return SUCCESS;
    }
	
	@Action(value="user-watch/index-watched/{userId}",
		results={
            @Result(name="success", location="user-watch/index-watched.ftl")
		}
	)
    public String IndexWatchedAction() throws Exception {
		user = userDao.findById(userId);
		if(user == null) {
			return ERROR;
		}
    	userWatches = userService.paginateUserWatchedes(
    			user.getId(), DISP_USER_WATCHE_NUM, page);
		return SUCCESS;
    }
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public User getUser() {
		return user;
	}
	
    public SimplePager<UserWatch> getUserWatches() {
    	return userWatches;
    }
}