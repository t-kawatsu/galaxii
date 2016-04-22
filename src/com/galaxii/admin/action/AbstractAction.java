package com.galaxii.admin.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxii.common.entity.AdminUser;
import com.galaxii.common.interceptor.LoginAuthenticationAware;
import com.galaxii.common.interceptor.LoginAuthorizationAware;
import com.galaxii.common.util.SessionUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.Before;

@ParentPackage("base")
@Namespace("/admin")
@InterceptorRefs({
    @InterceptorRef("myAdminStack"),
})
public abstract class AbstractAction extends ActionSupport 
	implements 
		SessionAware,
		ServletRequestAware, 
		LoginAuthenticationAware, 
		LoginAuthorizationAware<AdminUser> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TOP = "admin-top";
	public static final String LOGIN = "admin-login";
	protected static final int DISP_ITEMS_NUM_PAR_PAGE = 10;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected HttpServletRequest request;
	protected SessionUtil sessUtil;
	protected AdminUser user;

    @Before
    public void beforeAction() {}

    @Override
    public boolean getIsLogined() {
        return sessUtil.getCurrentUser() != null;
    }

    @Override
    public AdminUser getCurrentUser() {
        return (AdminUser) sessUtil.getCurrentUser();
    }

	@Override
	public void removeCurrentUser() {
        sessUtil.removeCurrentUser();
	}
	
	@Override
    public void setCurrentUser(AdminUser user) {
        sessUtil.putCurrentUser(user);
    }

	@Override
	public boolean isSecured() {
		return false;
	}

	@Override
	public String getLoginActionName() {
		return LOGIN;
	}
	
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setSession(Map<String, Object> session) {
        this.sessUtil = new SessionUtil(
            session,
            ServletActionContext.getRequest().getContextPath(),
            ServletActionContext.getActionMapping().getNamespace(),
            ServletActionContext.getActionMapping().getName());
    }

}
