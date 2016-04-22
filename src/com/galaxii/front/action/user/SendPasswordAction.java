package com.galaxii.front.action.user;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.galaxii.common.dao.UserDao;
import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserAccountType;
import com.galaxii.common.entity.UserStatus;
import com.galaxii.common.service.MailService;
import com.galaxii.common.util.crypt.BlowfishCrypt;
import com.galaxii.front.action.AbstractAction;
import com.galaxii.front.form.SendPasswordForm;

public class SendPasswordAction extends AbstractAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
    private UserDao userDao;
    @Resource
    private MailService mailService;
    @Resource
    private SendPasswordForm sendPasswordForm;

    @Action(value="user/send-password",
        results={
            @Result(name="input", location="user/send-password.ftl"),
            @Result(name="success", location="user/send-password-complete.ftl")
        }   
    )
	@Override
    public String execute() throws Exception {
        if(!"POST".equals(request.getMethod())) {
            return INPUT;
        }   
        if(!sendPasswordForm.validate(this)) {
            return INPUT;
        }   

        String mailAddress = sendPasswordForm.getMailAddress();
        User user = userDao.findByMailAddressAndStatus(mailAddress, UserStatus.LIVE);
        if(user == null) {
            return INPUT;
        }   
        if(!user.hasAccountType(UserAccountType.PROPER)) {
            // FIXME Facebookアカウントログインの場合、パスワードがないのでとりあえずメール送信しない
            return SUCCESS;
        }

        mailService.sendPassword(mailAddress, BlowfishCrypt.decrypt(user.getPassword()));

        return SUCCESS;
    }

    public void setSendPasswordForm(SendPasswordForm sendPasswordForm) {
        this.sendPasswordForm = sendPasswordForm;
    }   

    public SendPasswordForm getSendPasswordForm() {
        return sendPasswordForm;
    }   

}
