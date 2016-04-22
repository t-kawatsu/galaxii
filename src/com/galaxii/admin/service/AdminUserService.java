package com.galaxii.admin.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.dao.AdminUserDao;
import com.galaxii.common.entity.AdminUser;
import com.galaxii.common.entity.AdminUserRole;

@Transactional
//@Scope("prototype")
@Service
public class AdminUserService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    protected AdminUserDao adminUserDao;
    
    public AdminUser create(String name, String password, AdminUserRole  role)
    		throws Exception {
    	return adminUserDao.create(name, password, role);
    }
    
    public AdminUser login(String name, String password) 
    		throws Exception {
        AdminUser user = adminUserDao.find(name, password);
        return user;
    }
  
}