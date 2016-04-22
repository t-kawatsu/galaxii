package com.galaxii.front.action.tw_user;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import twitter4j.Twitter;

import com.galaxii.common.entity.LanguageCode;
import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserStatus;


public class CreateAction extends AbstractTwAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value="tw-user/create",
		results={
			@Result(name="input", location="user/create.ftl"),
			@Result(name="success", location="${loginUrl}", type="redirect")
		}
	)
    public String execute() throws Exception {
		loginUrl = detectLoginUrl("/tw-user/create-complete");
        return SUCCESS;
    }

	@Action(value="tw-user/create-complete",
		results={
			@Result(name="input", location="user/create.ftl"),
			@Result(name="exists", location="/", type="redirect"),
			@Result(name="success", location="user/create-complete.ftl")
		}
	)
    public String completeAction() throws Exception {
		String oauthToken = request.getParameter("oauth_token");
		String oauthVerifier = request.getParameter("oauth_verifier");
		if(StringUtils.isEmpty(oauthToken) || StringUtils.isEmpty(oauthVerifier)) {
			// denied
			return LOGIN;
		}

		Twitter twitter = getAccessTokenObteinedClient(oauthVerifier);
		Long twId = twitter.verifyCredentials().getId();
		User user = userDao.findByTwIdAndState(twId, UserStatus.LIVE);
		if(user != null) {
			//already exists
			setCurrentUser(user);
			return "exists";
		}
		
		// create twUser if not exists.
		userForm = convertClientUserBean2UserForm(twitter);
		if(!userForm.validate(this)) {
			return INPUT;
		}
		user = twUserService.createUser(
				userForm, LanguageCode.JA, request.getHeader("User-Agent"));
		try {
	    	userImageService.clearTmpImageDir(user.getId());
	    	userImageService.createFromUrl(user, 
	    			new URL(twitter.verifyCredentials().getBiggerProfileImageURLHttps()));
		} catch(Exception e) {
		}
		setCurrentUser(user);

		return SUCCESS;
	}

}
