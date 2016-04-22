package com.galaxii.common.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class UserInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {}

	public String intercept(ActionInvocation invocation) throws Exception { 
        return invocation.invoke();
	}

}
