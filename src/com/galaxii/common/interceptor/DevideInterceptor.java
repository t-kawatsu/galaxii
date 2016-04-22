package com.galaxii.common.interceptor;

import nl.bitwalker.useragentutils.DeviceType;
import nl.bitwalker.useragentutils.UserAgent;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

// http://stackoverflow.com/questions/173846/struts2-how-to-do-dynamic-url-redirects
// http://stackoverflow.com/questions/2999829/redirect-to-another-action-in-an-interceptor-in-struts-2
public class DevideInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private boolean noDevide;

	public void setNoDevide(String noDevide) {
		this.noDevide = Boolean.valueOf(noDevide);
	}

	public void init() {}

	public String intercept(ActionInvocation invocation) throws Exception { 
		// 振り分けしない設定
		if(noDevide) {
			return invocation.invoke();
		}

		String ua = ServletActionContext.getRequest().getHeader("User-Agent");
		String namespace = invocation.getProxy().getNamespace();

        // Facebookからのアクセスは許可
        if(ua.startsWith("facebookexternalhit")) {
            return invocation.invoke();
        }

		// facebook アプリかどうか
		boolean isFacebookApp = "https://apps.facebook.com".equals
			( ServletActionContext.getRequest().getHeader("Origin") );

		// デバイスにより振り分け
		UserAgent userAgent = new UserAgent(ua); 	
		DeviceType deviceType = userAgent.getOperatingSystem().getDeviceType();
		boolean isSmartphone = 
			deviceType == DeviceType.TABLET || deviceType == DeviceType.MOBILE;

		if(isSmartphone) {
			// smartphone
			if(!namespace.equals("/sp") && !namespace.equals("/admin")) {
				return "sp-top";
			}
		} else if(isFacebookApp) {
			// facebook app
			if(!namespace.equals("/fb")) {
				return "fb-top";
			}
		} else { 
			// pc
			if(!namespace.equals("/") && !namespace.equals("/admin")) {
				return "top";
			}
		}

		return invocation.invoke();
	}

}
