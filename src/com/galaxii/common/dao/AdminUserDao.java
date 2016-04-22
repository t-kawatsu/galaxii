package com.galaxii.common.dao;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.AdminUser;
import com.galaxii.common.entity.AdminUserRole;
import com.galaxii.common.util.crypt.BlowfishCrypt;

@Repository
@Transactional
public class AdminUserDao extends AbstractDao<AdminUser> {
	
	public AdminUser create(String name, String password, AdminUserRole role)
    		throws Exception {
        AdminUser user = new AdminUser();
        user.setName(name);
        user.setPassword( BlowfishCrypt.encrypt(password) );
        user.setRoles(role);
        save(user);
        return user;
    }
	
	public AdminUser find(String name, String plainPassword) throws HibernateException, Exception {
		if(name == null || plainPassword == null) {
			return null;
		}
	
		StringBuilder sb = new StringBuilder();
		sb.append("FROM AdminUser u WHERE ");
		sb.append("u.name = :name AND u.password = :password ");
	
		return (AdminUser) getSession().createQuery(sb.toString())
			.setString("name", name)
			.setString("password", BlowfishCrypt.encrypt(plainPassword) )
			.uniqueResult();
	}
}
