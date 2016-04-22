package com.galaxii.common.service.facebook;

import static com.restfb.util.UrlUtils.urlEncode;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookResponseContentException;

public class MyDefaultFacebookClient 
	extends DefaultFacebookClient 
	implements MyFacebookClient {

	public static final String CODE_ATTRIBUTE_NAME = "code";
	protected static final String FACEBOOK_OAUTH_ENDPOINT_URL = "https://www.facebook.com/dialog/oauth";

	public MyDefaultFacebookClient() {
		super();
	}

	public MyDefaultFacebookClient(String accessToken) {
		super(accessToken);
	}
	
	public String buildPictureUrl(String fbId, PictureSize pictureSize) {
		return FACEBOOK_GRAPH_ENDPOINT_URL + "/" + fbId + "/picture?type=" + pictureSize.toString();
	}

	public String buildLoginUrl(String appId, String redirectUri, DisplayMode displayMode, String[] scopes) {
	    StringBuilder sb = new StringBuilder();

		sb.append(FACEBOOK_OAUTH_ENDPOINT_URL)
		.append("?client_id=")
		.append(appId)
		.append("&redirect_uri=")
		.append(urlEncode(redirectUri))
		.append("&display=")
		.append(displayMode.getName())
		//.append("&response_type=")
		//.append("token")
		.append("&scope=")
		.append(urlEncode( StringUtils.join(scopes, ",") ));

		return sb.toString();
	}

	public void logout(String accessToken) {
		// https://developers.facebook.com/docs/howtos/login/server-side-logout/
	}

	public AccessToken obtainAccessToken(String appId, String appSecret, String code, String redirectUrl) {
		verifyParameterPresence("appId", appId);
		verifyParameterPresence("appSecret", appSecret);

		String response =
			makeRequest("oauth/access_token", 
				Parameter.with("code", code), 
				Parameter.with("redirect_uri", redirectUrl),
				Parameter.with("client_id", appId), 
				Parameter.with("client_secret", appSecret));
		try {
			return AccessToken.fromQueryString(response);
		} catch (Throwable t) {
			throw new FacebookResponseContentException("Unable to extract access token from response.", t); 
		}
	}

	public <T> List<T> search(String type, Class<T> clazz, String query, String fields, int limit) 
		throws FacebookException {
		Connection<T> publicSearch = fetchConnection(
			"search", clazz, 
			Parameter.with("q", query),
			Parameter.with("fields", fields),
			Parameter.with("limit", limit),
			Parameter.with("type", type));
		if(publicSearch == null) {
			return null;
		}
		return publicSearch.getData();
	}
}
