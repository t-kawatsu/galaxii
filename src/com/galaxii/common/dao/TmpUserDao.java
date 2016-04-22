package com.galaxii.common.dao;

import java.util.Date;

import com.galaxii.common.entity.TmpUser;
import com.galaxii.common.util.crypt.BlowfishCrypt;
import com.galaxii.common.util.crypt.MD5Crypt;
import com.galaxii.front.form.UserForm;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class TmpUserDao extends AbstractDao<TmpUser> {

    public TmpUser findByToken(final String token) {
        if(token == null) {
            return null;
        }   

        return (TmpUser)getSession().createQuery(
            "FROM TmpUser tu WHERE tu.token = :token")
            .setString("token", token).uniqueResult();
    }   

    public TmpUser create(UserForm userForm) 
        throws Exception {
        TmpUser tmpUser = new TmpUser();
        tmpUser.setNickname(userForm.getNickname());
        tmpUser.setMailAddress(userForm.getMailAddress());
        tmpUser.setPassword( BlowfishCrypt.encrypt(userForm.getPassword()) );
        tmpUser.setSex(userForm.getSex());
        tmpUser.setExpireAt(DateUtils.addDays(new Date(), 1));
        int generatedValue = (Integer) save(tmpUser);
        tmpUser.setToken(MD5Crypt.crypt(String.valueOf(generatedValue)));
        getSession().update(tmpUser);
        return tmpUser;
    }   
}