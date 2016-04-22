package com.galaxii.common.dao;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.type.CustomType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.User;
import com.galaxii.common.entity.UserAccountType;
import com.galaxii.common.entity.UserStatus;
import com.galaxii.common.hibernate.type.UserAccountTypeUserType;
import com.galaxii.common.util.crypt.BlowfishCrypt;

@Repository
@Transactional
public class UserDao extends AbstractDao<User> {
	
	public User findById(final Serializable id) {
		if(id == null) {
			return null;
		}
		return (User) getSession().createQuery(
				"FROM User u LEFT JOIN fetch u.fbUser LEFT JOIN fetch u.twUser WHERE u.id = :id")
				.setParameter("id", id).uniqueResult();
	}
	
	public User findByFbIdAndState(String fbId, UserStatus status) {
		if(fbId == null) {
			return null;
	    }   
	    return (User) getSession().createQuery(
	    		"FROM User u JOIN fetch u.fbUser fu WHERE fu.fbId = :fbId AND u.status = :status")
	            .setString("fbId", fbId)
	            .setParameter("status", status).uniqueResult();
	}
	
    public User findByTwIdAndState(Long twId, UserStatus status) {
        if(twId == null) {
            return null;
        }   
        return (User) getSession().createQuery(
            "FROM User u JOIN fetch u.twUser tu WHERE tu.twId = :twId AND u.status = :status")
            .setLong("twId", twId)
            .setParameter("status", status).uniqueResult();
    }

	public int countByMailAddressAndStatusAndAccountType(
		final String mailAddress, final UserStatus status, final UserAccountType accountType) {
		if(mailAddress == null || status == null || accountType == null) {
			return 0;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT count(*) FROM User u ");
		sb.append("WHERE u.mailAddress = :mailAddress AND u.status = :status AND bitflag(u.accountTypes, :accountTypes) > 0 ");

		return detectCountValue( getSession().createQuery(sb.toString())
			.setString("mailAddress", mailAddress)
			.setParameter("status", status)
			.setParameter("accountTypes", accountType, new CustomType(new UserAccountTypeUserType(), null) )) ;
	}

	public int countByMailAddressAndStatus(
		final String mailAddress, final UserStatus status) {
		if(mailAddress == null || status == null) {
			return 0;
		}
		return detectCountValue(getSession().createQuery(
			"SELECT count(*) FROM User u WHERE u.mailAddress = :mailAddress AND u.status = :status ")
			.setString("mailAddress", mailAddress)
			.setParameter("status", status));
	}

	public int countByMailAddressAndPasswordAndStatus(
		final String mailAddress, final String plainPassword, final UserStatus status) throws HibernateException, Exception {
		if(mailAddress == null || plainPassword == null) {
			return 0;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT count(*) FROM User u ");
		sb.append("WHERE u.mailAddress = :mailAddress AND u.status = :status AND u.password = :password ");

		return detectCountValue( getSession().createQuery(sb.toString())
			.setString("mailAddress", mailAddress)
			.setString("password", BlowfishCrypt.encrypt(plainPassword) )
			.setParameter("status", status) );
	}

	public User findByMailAddressAndStatus(final String mailAddress, final UserStatus status) {
		if(mailAddress == null || status == null) {
			return null;
		}

		return (User)getSession().createQuery(
			"FROM User u WHERE u.mailAddress = :mailAddress AND u.status = :status")
			.setString("mailAddress", mailAddress)
			.setParameter("status", status).uniqueResult();
	}

	public User findByMailAddressAndPasswordAndStatus(
		String mailAddress, String plainPassword, UserStatus status) throws HibernateException, Exception {
		if(mailAddress == null || plainPassword == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("FROM User u WHERE ");
		sb.append("u.mailAddress = :mailAddress AND u.status = :status AND u.password = :password ");

		return (User) getSession().createQuery(sb.toString())
			.setString("mailAddress", mailAddress)
			.setString("password", BlowfishCrypt.encrypt(plainPassword) )
			.setParameter("status", status).uniqueResult();
	}

	public User updatePassword(User user, String password) throws Exception {
		String encryptedPassword = BlowfishCrypt.encrypt(password);
		user.setPassword(encryptedPassword);
		this.update(user);
		return user;
	}
	
    public User findByToken(final String token) {
        if(token == null) {
            return null;
        }
        return (User)getSession().createQuery(
            "FROM User u WHERE u.token = :token")
            .setString("token", token).uniqueResult();
    }
}
