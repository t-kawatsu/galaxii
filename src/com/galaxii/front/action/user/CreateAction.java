package com.galaxii.front.action.user;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.LanguageSettingDao;
import com.galaxii.common.dao.TmpUserDao;
import com.galaxii.common.dao.UserDao;
import com.galaxii.common.entity.LanguageCode;
import com.galaxii.common.entity.TmpUser;
import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserAccountType;
import com.galaxii.common.entity.UserStatus;
import com.galaxii.common.service.MailService;
import com.galaxii.common.service.UserService;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.UserForm;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private UserDao userDao;
	@Resource
	private TmpUserDao tmpUserDao;
	@Resource
	private UserForm userForm;
    @Resource
    private MailService mailService;
    @Resource
    private UserService userService;
    @Resource
    private LanguageSettingDao languageSettingDao;
	
	private TmpUser tmpUser;
	private String token;

	@Action(value="user/create", 
		results={ 
			@Result(name="input", location="user/create.ftl"),
			@Result(name="success", location="user/create-confirm.ftl")
		}
	)
	@Override
    public String execute() throws Exception {
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }
        if(!userForm.validate(this)) {
            return INPUT;
        }
        // create tmp data
        tmpUser = tmpUserDao.create(userForm);
        // send mail to tmp user
        if(tmpUser.getMailAddress().startsWith(getText("app.testUser.mailAddressPrefix"))) {
        	mailService.sendCreateTmpUserComplete(getText("app.mail.support"), tmpUser.getToken());
        } else {
        	mailService.sendCreateTmpUserComplete(tmpUser.getMailAddress(), tmpUser.getToken());
        }
        return SUCCESS;
    }
	
	@Action(value="user/create-complete", 
		results={ 
            @Result(name="input", location="user/create-complete.ftl"),
            @Result(name="expired", location="user/create-expired.ftl"),
			@Result(name="success", location="user/create-complete.ftl")
		}
	)
	public String completeAction() throws Exception {
        if(StringUtils.isEmpty(token)) {
            throw new Exception();
        }   

        TmpUser tmpUser = tmpUserDao.findByToken(token);
        if(tmpUser == null) {
            return "expired";
        }   

        int userCnt = userDao.countByMailAddressAndStatusAndAccountType(
            tmpUser.getMailAddress(), UserStatus.LIVE, UserAccountType.PROPER);
        if(0 < userCnt) {
            return "success";
        }   

        User user = userService.create(tmpUser, LanguageCode.JA, 
            request.getHeader("User-Agent"), UserAccountType.PROPER);
        tmpUserDao.delete(tmpUser);
        
        setCurrentUser(user);
        
		return SUCCESS;
	}
	
	public UserForm getUserForm() {
		return userForm;
	}
	
	public void setUserForm(UserForm userForm) {
		this.userForm = userForm;
	}

	public TmpUser getTmpUser() {
		return tmpUser;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
}
