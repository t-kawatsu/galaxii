package com.galaxii.front.action.fb_user;


import java.security.InvalidParameterException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserAccountType;
import com.galaxii.common.entity.UserStatus;
import com.restfb.exception.FacebookException;

public class MergeAction extends AbstractFbAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value="fb-user/merge",
		results={
			@Result(name="success", location="${loginUrl}", type="redirect")
		}
	)
    public String execute() throws Exception {
		User user = getCurrentUser();
		if(user == null) {
			throw new Exception();
		}
		if(user.hasAccountType(UserAccountType.FACEBOOK)) {
			throw new Exception();
		}
		loginUrl = detectLoginUrl("/fb-user/merge-complete");
        return SUCCESS;
    }

	@Action(value="fb-user/merge-complete",
        results={@Result(name="success", location="user/update", type="redirect")}
	)
    public String completeAction() throws Exception {
		com.galaxii.common.entity.User user = getCurrentUser();
		if(user == null) {
			throw new Exception();
		}
		if(user.hasAccountType(UserAccountType.FACEBOOK)) {
			throw new Exception();
		}

		String code = request.getParameter("code");
		if(code == null) {
			throw new InvalidParameterException();
		}

		com.restfb.types.User facebookUser;
		try {
			facebookClient = getAccessTokenObteinedFacebookClient("/fb-user/merge-complete", code);
			facebookUser = facebookClient.fetchObject("me", com.restfb.types.User.class);
		} catch (FacebookException fe) {
			return ERROR;
		} catch (Exception e) {
			throw e;
		}
		
		if(facebookUser.getId() == null) {
			return SUCCESS;
		}
		
		User _user = userDao.findByFbIdAndState(facebookUser.getId(), UserStatus.LIVE);
		if(_user != null) {
			// 既に存在する
			if(_user.getId().equals(user.getId())) {
				// 既にひもづけられている
				return SUCCESS;
			} else {
				// 他のユーザーにひもづけられている
				// TODO redirect and error message
				throw new Exception();
			}
		} 

		user = fbUserService.merge(user, facebookUser.getId());
		setCurrentUser(user);
		return SUCCESS;
	}
}
