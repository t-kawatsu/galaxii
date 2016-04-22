package com.galaxii.common.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.dao.FbUserDao;
import com.galaxii.common.dao.UserDao;
import com.galaxii.common.entity.FbUser;
import com.galaxii.common.entity.LanguageCode;
import com.galaxii.common.entity.TmpUser;
import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserAccountType;
import com.galaxii.front.form.FbUserForm;

@Transactional
//@Scope("prototype")
@Service
public class FbUserService {
	
	@Resource
	private UserDao userDao;
    @Resource
    private FbUserDao fbUserDao;
    @Resource
    private UserService userService;

    public User createUser(FbUserForm fbUserForm, 
    	LanguageCode languageCode, String userAgent) throws Exception {
    	
        TmpUser tmpUser = new TmpUser();
        Date defaultValue = null;
        ConvertUtils.register(new DateConverter(defaultValue), Date.class);
        BeanUtils.copyProperties(tmpUser, fbUserForm);
        User user = userService.create(tmpUser, languageCode, userAgent, UserAccountType.FACEBOOK);

        FbUser fbUser = new FbUser();
        fbUser.setId(user.getId());
        fbUser.setAccountName(fbUserForm.getNickname());
        fbUser.setFbId(fbUserForm.getFbId());
        fbUser.setIsPrivate(true);
        fbUserDao.save(fbUser);

        user.setFbUser(fbUser);
        return user;
    }
    
	/**
	 * 
	 * @param user
	 * @return true become private <br/> false become public
	 * @throws Exception
	 */
	public boolean toggleUserPrivate(User user) throws Exception {
		if(user == null || !user.isFacebookUser()) {
			throw new Exception();
		}
		user.getFbUser().setIsPrivate(!user.getFbUser().getIsPrivate());
		fbUserDao.update(user.getFbUser());
		return user.getFbUser().getIsPrivate();
	}
	
	public User merge(User user, String fbId) throws Exception {
		
		FbUser fbUser = new FbUser();
	    fbUser.setId(user.getId());
	    fbUser.setFbId(fbId);
	    fbUser.setIsPrivate(true);
	    fbUserDao.save(fbUser);
	    
	    user.addAccountType(UserAccountType.FACEBOOK);
	    userDao.update(user);
	    user.setFbUser(fbUser);
	    return user;
	}
}