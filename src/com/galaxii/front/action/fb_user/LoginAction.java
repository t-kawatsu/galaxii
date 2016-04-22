package com.galaxii.front.action.fb_user;


import java.security.InvalidParameterException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.LanguageCode;
import com.galaxii.common.entity.UserStatus;
import com.restfb.exception.FacebookException;

public class LoginAction extends AbstractFbAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value="fb-user/login",
		results={
			@Result(name="success", location="${loginUrl}", type="redirect")
		}
	)
    public String execute() throws Exception {
		loginUrl = detectLoginUrl("/fb-user/login-complete");
        return SUCCESS;
    }

	@Action(value="fb-user/login-complete",
		results={
			@Result(name="input", location="user/create", type="redirect"),
			@Result(name="success", location="/", type="redirect")
		}
	)
    public String completeAction() throws Exception {
		String code = request.getParameter("code");
		if(code == null) {
			throw new InvalidParameterException();
		}

		com.restfb.types.User facebookUser;
		try {
			facebookClient = getAccessTokenObteinedFacebookClient("/fb-user/login-complete", code);
			facebookUser = facebookClient.fetchObject("me", com.restfb.types.User.class);
		} catch (FacebookException fe) {
			return INPUT;
		} catch (Exception e) {
			throw e;
		}
		
		if(facebookUser.getId() == null) {
			return INPUT;
		}

		com.galaxii.common.entity.User user = 
				userDao.findByFbIdAndState(facebookUser.getId(), UserStatus.LIVE);
		if(user == null) {
			userForm = convertFbClientUserBean2UserForm(facebookUser);
			if(!userForm.validate(this)) {
				return INPUT;
			}
			
			// create fbUser if not exists.
			user = fbUserService.createUser(
				userForm,
				LanguageCode.JA, 
				request.getHeader("User-Agent"));
			try {
				registFbUserImage(user, user.getFbUser().getFbId());
			} catch(Exception e) {
				
			}
			//return "exists";
		} else {
			user = userService.login(user, true);
		}
		
		setCurrentUser(user);
		return SUCCESS;
	}
}
