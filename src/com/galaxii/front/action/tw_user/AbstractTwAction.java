package com.galaxii.front.action.tw_user;

import javax.annotation.Resource;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.galaxii.common.dao.UserDao;
import com.galaxii.common.service.TwUserService;
import com.galaxii.common.service.UserImageService;
import com.galaxii.common.service.UserService;
import com.galaxii.common.service.twitter.MyTwitterClient;
import com.galaxii.common.util.UrlHelper;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.TwUserForm;

public class AbstractTwAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name="twUserForm")
	protected TwUserForm userForm;
	@Resource
	protected UserDao userDao;
	@Resource
	protected UserService userService;
	@Resource
	protected TwUserService twUserService;
	@Resource
	protected UserImageService userImageService;
	
	
	protected Twitter twitter;

	protected String loginUrl;

    protected String detectLoginUrl(String callbackActionName) throws Exception {
		twitter = MyTwitterClient.factoryTwitter(
				getText("app.twitter.key"), getText("app.twitter.secret") );
		UrlHelper urlHelper = new UrlHelper();
		RequestToken requestToken = twitter.getOAuthRequestToken(
			urlHelper.buildUrl(callbackActionName, null, true, true) );
		sessUtil.putNamespace("twRequestToken", requestToken);
		return requestToken.getAuthenticationURL();
    }
    
    public Twitter getAccessTokenObteinedClient(String oauthVerifier) 
    		throws TwitterException {
    	
		twitter = MyTwitterClient.factoryTwitter(
			getText("app.twitter.key"), getText("app.twitter.secret") );
		
		AccessToken accessToken = twitter.getOAuthAccessToken(
			(RequestToken)sessUtil.getNamespace("twRequestToken"), oauthVerifier );
		
		sessUtil.removeNamespace("twRequestToken");
		sessUtil.putNamespace("twToken", accessToken.getToken());
		sessUtil.putNamespace("twTokenSecret", accessToken.getTokenSecret());
		
		return twitter;
    }
    
    public TwUserForm convertClientUserBean2UserForm(Twitter twitter) throws TwitterException {
    	userForm.setTwId(twitter.getId());
    	userForm.setNickname(twitter.getScreenName());
    	//userForm.setGender(twitter);
    	//userForm.setBirthday(twitter.verifyCredentials());
    	//userForm.setMailAddress(facebookUser.getEmail());
    	//userForm.setMessage(facebookUser.get)
    	return userForm;
    }
    
	public String getLoginUrl() {
		return loginUrl;
	}
	
	public TwUserForm getUserForm() {
		return userForm;
	}

}
