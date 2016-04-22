package com.galaxii.admin.action.user;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.admin.action.AbstractAction;
import com.galaxii.admin.form.AdminLoginForm;
import com.galaxii.admin.service.AdminUserService;
import com.galaxii.common.entity.AdminUser;

public class LoginAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private AdminUserService adminUserService;
	@Resource
	private AdminLoginForm adminLoginForm;

	@Action(value="user/login", 
		results={ 
			@Result(name="input", location="user/login.ftl")
		}
	)
    public String loginAction() throws Exception {
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }
        if(!adminLoginForm.validate(this)) {
            return INPUT;
        }   
        AdminUser user = adminUserService.login(
        		adminLoginForm.getName(), 
        		adminLoginForm.getPassword() );
        setCurrentUser(user);
        return TOP;
    }

    public void setAdminLoginForm(AdminLoginForm adminLoginForm) {
        this.adminLoginForm = adminLoginForm;
    }
    
    public AdminLoginForm getAdminLoginForm() {
    	return adminLoginForm;
    }
    
    @Override
    public boolean isSecured() {
    	return true;
    }
}
