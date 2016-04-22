package com.galaxii.common.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.TwUser;

@Repository
@Transactional
public class TwUserDao extends AbstractDao<TwUser> {

}
