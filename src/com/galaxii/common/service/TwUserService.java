package com.galaxii.common.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.dao.TwUserDao;
import com.galaxii.common.dao.UserDao;
import com.galaxii.common.entity.LanguageCode;
import com.galaxii.common.entity.TmpUser;
import com.galaxii.common.entity.TwUser;
import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserAccountType;
import com.galaxii.front.form.TwUserForm;

@Transactional
//@Scope("prototype")
@Service
public class TwUserService {
	
	@Resource
	private UserDao userDao;
    @Resource
    private TwUserDao twUserDao;
    @Resource
    private UserService userService;

    public User createUser(TwUserForm twUserForm, 
    	LanguageCode languageCode, String userAgent) throws Exception {
    	
        TmpUser tmpUser = new TmpUser();
        Date defaultValue = null;
        ConvertUtils.register(new DateConverter(defaultValue), Date.class);
        BeanUtils.copyProperties(tmpUser, twUserForm);
        User user = userService.create(tmpUser, languageCode, userAgent, UserAccountType.TWITTER);

        TwUser twUser = new TwUser();
        twUser.setId(user.getId());
        twUser.setAccountName(twUserForm.getNickname());
        twUser.setTwId(twUserForm.getTwId());
        twUser.setIsPrivate(true);
        twUserDao.save(twUser);

        user.setTwUser(twUser);
        return user;
    }
    
	/**
	 * 
	 * @param user
	 * @return true become private <br/> false become public
	 * @throws Exception
	 */
	public boolean toggleUserPrivate(User user) throws Exception {
		if(user == null || !user.isTwitterUser()) {
			throw new Exception();
		}
		user.getTwUser().setIsPrivate(!user.getTwUser().getIsPrivate());
		twUserDao.update(user.getTwUser());
		return user.getTwUser().getIsPrivate();
	}
	
	public User merge(User user, Long twId) throws Exception {
		
        TwUser twUser = new TwUser();
        twUser.setId(user.getId());
        twUser.setTwId(twId);
        twUser.setIsPrivate(true);
        twUserDao.save(twUser);
	    
	    user.addAccountType(UserAccountType.TWITTER);
	    userDao.update(user);
	    user.setTwUser(twUser);
	    return user;
	}
}