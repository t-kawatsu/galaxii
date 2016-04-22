package com.galaxii.common.service.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class MyTwitterClient {

	private String key;
	private String secret;
	private Twitter twitter;

	public MyTwitterClient(String key, String secret) {
		this.key = key;
		this.secret = secret;
		this.twitter = factoryTwitter(key, secret);
	}

	public static Twitter factoryTwitter(String key, String secret) {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey( key );
		builder.setOAuthConsumerSecret( secret );
		Configuration configuration = builder.build();
		TwitterFactory factory = new TwitterFactory(configuration);
		return factory.getInstance();
	}

	public AccessToken obtainAccessToken(RequestToken requestToken, String oauthVerifier) 
		throws Exception {
		return twitter.getOAuthAccessToken( requestToken, oauthVerifier );
	}
}
