package com.galaxii.front.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxii.common.entity.User;
import com.galaxii.common.service.UserInformationService;
import com.galaxii.common.util.SessionUtil;
import com.galaxii.front.form.LoginForm;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.Before;

@ParentPackage("base")
@Namespace("/")
@ResultPath("/WEB-INF/content/front")
@InterceptorRefs({
    @InterceptorRef("myStack"),
})
abstract public class AbstractAction extends ActionSupport
	implements ServletRequestAware, ServletResponseAware, SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected static String TOP   = "top";
    protected static String PJAX   = "pjax";
    protected static String REQUIRE_COMMUNITY_MEMBER   = "require-community-member";
    
    protected static Integer SUB_CONTENTS_COMMUNITY_USERS_LIMIT = 15;
    
    @Resource
	protected UserInformationService userInformationService;
	@Resource
	protected LoginForm loginForm;

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected SessionUtil sessUtil;

    @Before
    public void beforeAction() {}

    public boolean getIsLogined() {
        // TODO interseptor か before へ移動
        return sessUtil.getCurrentUser() != null;
    }

    public User getCurrentUser() {
        return (User) sessUtil.getCurrentUser();
    }

    protected void setCurrentUser(User user) {
        sessUtil.putCurrentUser(user);
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.sessUtil = new SessionUtil(
            session,
            ServletActionContext.getRequest().getContextPath(),
            ServletActionContext.getActionMapping().getNamespace(),
            ServletActionContext.getActionMapping().getName());
    }

	public LoginForm getLoginForm() {
		return loginForm;
	}
	
	public boolean isPjax() {
		return !StringUtils.isEmpty(request.getHeader("X-PJAX"));
	}
}
