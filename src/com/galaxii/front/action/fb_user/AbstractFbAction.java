package com.galaxii.front.action.fb_user;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.Resource;

import com.galaxii.common.dao.UserDao;
import com.galaxii.common.entity.User;
import com.galaxii.common.service.FbUserService;
import com.galaxii.common.service.UserImageService;
import com.galaxii.common.service.UserService;
import com.galaxii.common.service.facebook.DisplayMode;
import com.galaxii.common.service.facebook.MyDefaultFacebookClient;
import com.galaxii.common.service.facebook.MyFacebookClient;
import com.galaxii.common.service.facebook.PictureSize;
import com.galaxii.common.util.UrlHelper;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.FbUserForm;


public abstract class AbstractFbAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name="fbUserForm")
	protected FbUserForm userForm;
	@Resource
	protected UserDao userDao;
	@Resource
	protected FbUserService fbUserService;
	@Resource
	protected UserService userService;
	@Resource
	protected UserImageService userImageService;
	
	protected MyFacebookClient facebookClient = 
			new MyDefaultFacebookClient();

	protected String loginUrl;

    protected String detectLoginUrl(String callbackActionName) throws Exception {
		UrlHelper urlHelper = new UrlHelper();
		return facebookClient.buildLoginUrl(
				getText("app.facebook.appId"), 
				urlHelper.buildUrl(callbackActionName, null, true, true), 
				DisplayMode.PAGE, 
				new String[]{ "email" } );
    }
    
    public MyFacebookClient getAccessTokenObteinedFacebookClient(
    		String callbackActionName, String code) {
		UrlHelper urlHelper = new UrlHelper();
		MyFacebookClient.AccessToken accessToken = 
			facebookClient.obtainAccessToken(
					getText("app.facebook.appId"), 
					getText("app.facebook.secret"), 
					code, 
					urlHelper.buildUrl(callbackActionName, null, true, true) );
		return new MyDefaultFacebookClient(accessToken.getAccessToken());
    }
    
    public FbUserForm convertFbClientUserBean2UserForm(com.restfb.types.User facebookUser) {
    	userForm.setFbId(facebookUser.getId());
    	userForm.setNickname(facebookUser.getName());
    	userForm.setGender(facebookUser.getGender());
    	userForm.setBirthday(facebookUser.getBirthdayAsDate());
    	userForm.setMailAddress(facebookUser.getEmail());
    	//userForm.setMessage(facebookUser.get)
    	return userForm;
    }
    
    public void registFbUserImage(User user, String fbId) throws MalformedURLException, Exception {
    	userImageService.clearTmpImageDir(user.getId());
    	userImageService.createFromUrl(user, 
    			new URL(facebookClient.buildPictureUrl(fbId, PictureSize.large)) );
    }

	public String getLoginUrl() {
		return loginUrl;
	}
	
	public FbUserForm getUserForm() {
		return userForm;
	}
}
