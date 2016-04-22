package com.galaxii.front.action.tw_user;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import twitter4j.Twitter;

import com.galaxii.common.entity.LanguageCode;
import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserStatus;

public class LoginAction extends AbstractTwAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value="tw-user/login",
		results={
			@Result(name="success", location="${loginUrl}", type="redirect")
		}
	)
    public String execute() throws Exception {
		loginUrl = detectLoginUrl("/tw-user/login-complete");
        return SUCCESS;
    }

	@Action(value="tw-user/login-complete",
		results={
			@Result(name="input", location="user/login.ftl"),
			@Result(name="success", location="user/mypage", type="redirect")
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
		if(user == null) {
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
			//return "exists";
		} else {
			user = userService.login(user, true);
		}

		setCurrentUser(user);

		return SUCCESS;
	}

}
