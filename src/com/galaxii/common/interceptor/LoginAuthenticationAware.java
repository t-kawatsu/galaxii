package com.galaxii.common.interceptor;

public interface LoginAuthenticationAware {

	public boolean isSecured();

	public boolean getIsLogined();

	public String getLoginActionName();
}
