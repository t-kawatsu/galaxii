package com.galaxii.front.action.user_watch;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.UserDao;
import com.galaxii.common.entity.User;
import com.galaxii.common.service.UserService;
import com.galaxii.front.action.AbstractAction;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private UserService userService;
	@Resource
	private UserDao userDao;
	
	private Integer userId;
	private User user;
	private boolean isUserWatched;
	
	@Action(value="user-watch/delete-ajax/{userId}",
		results={
            @Result(name="success", location="user-watch/delete-complete.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
		
		if(!getIsLogined()) {
			return LOGIN;
		}
        
        user = userDao.findById(userId);
        if(user == null) {
        	return ERROR;
        }
        
        User myUser = getCurrentUser();
        
        if(myUser.equals(user.getId())) {
        	return ERROR;
        }
       
        if(!userService.isWatchUser(myUser.getId(), user.getId())) {
        	return SUCCESS;
        }
        
        userService.unWatchUser(myUser.getId(), user.getId());
        
        // user info
        userInformationService.deleteWatch(user, myUser);
        
		return SUCCESS;
    }
	
	@Action(value="user-watch/delete-small-ajax/{userId}",
		results={
            @Result(name="success", location="user-watch/_btn-small-watch-state.ftl")
		}
	)
    public String createAjaxAction() throws Exception {
		return this.execute();
    }
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public boolean getIsUserWatched() {
		return isUserWatched;
	}
	
	public User getUser() {
		return user;
	}
}