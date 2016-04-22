package com.galaxii.common.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.FbUser;

@Repository
@Transactional
public class FbUserDao extends AbstractDao<FbUser> {
	
}
