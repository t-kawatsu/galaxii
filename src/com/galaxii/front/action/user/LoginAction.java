package com.galaxii.front.action.user;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.entity.User;
import com.galaxii.common.service.UserService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.LoginForm;

public class LoginAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private UserService userService;

	@Action(value="user/login-ajax", 
		results={ 
			@Result(name="input", location="common/_login-form.ftl"),
			@Result(name="success", location="common/_logined.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!loginForm.validate(this)) {
            return INPUT;
        }   
        User user = userService.login(
        		loginForm.getMailAddress(), 
        		loginForm.getPassword(), 
        		loginForm.isKeep() );
        setCurrentUser(user);
        return SUCCESS;
    }
	
	@Action(value="user/login", 
		results={ 
			@Result(name="input", location="user/login.ftl"),
			@Result(name="success", location="/", type="redirect")
		}
	)
    public String loginAction() throws Exception {
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!loginForm.validate(this)) {
            return INPUT;
        }   
        User user = userService.login(
        		loginForm.getMailAddress(), 
        		loginForm.getPassword(), 
        		loginForm.isKeep() );
        setCurrentUser(user);
        return SUCCESS;
    }

    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }
    
    public LoginForm getLoginForm() {
    	return loginForm;
    }
}
