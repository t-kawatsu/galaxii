package com.galaxii.front.action.fb_user;

import java.security.InvalidParameterException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.LanguageCode;
import com.galaxii.common.entity.UserStatus;
import com.restfb.exception.FacebookException;


public class CreateAction extends AbstractFbAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value="fb-user/create",
		results={
			@Result(name="input", location="user/create.ftl"),
			@Result(name="success", location="${loginUrl}", type="redirect")
		}
	)
    public String execute() throws Exception {
		loginUrl = detectLoginUrl("/fb-user/create-complete");
        return SUCCESS;
    }

	@Action(value="fb-user/create-complete",
		results={
			@Result(name="input", location="user/create.ftl"),
			@Result(name="exists", location="/", type="redirect"),
			@Result(name="success", location="user/create-complete.ftl")
		}
	)
	// https://developers.facebook.com/docs/reference/dialogs/oauth/
    public String completeAction() throws Exception {
		String code = request.getParameter("code");
		if(code == null) {
			throw new InvalidParameterException();
		}

		com.restfb.types.User facebookUser;
		try {
			facebookClient = getAccessTokenObteinedFacebookClient("/fb-user/create-complete", code);
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
		if(user != null) {
			// already exists.
			setCurrentUser(user);
			return "exists";
		}

		userForm = convertFbClientUserBean2UserForm(facebookUser);
		if(!userForm.validate(this)) {
			return INPUT;
		}
		
		user = fbUserService.createUser(
			userForm,
			LanguageCode.JA, 
			request.getHeader("User-Agent"));
		
		try {
			registFbUserImage(user, user.getFbUser().getFbId());
		} catch(Exception e) {
			
		}
		setCurrentUser(user);
		return SUCCESS;
	}

}
