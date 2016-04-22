package com.galaxii.common.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginAuthenticateInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		if (invocation.getAction() instanceof LoginAuthenticationAware) {
			LoginAuthenticationAware aa = (LoginAuthenticationAware) invocation
					.getAction();
			if (!aa.getIsLogined() && !aa.isSecured()) {
				return aa.getLoginActionName();
			}
		}
		return invocation.invoke();
	}

}
