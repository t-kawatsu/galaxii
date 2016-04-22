package com.galaxii.common.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.UserViolation;

@Repository
@Transactional
public class UserViolationDao extends AbstractDao<UserViolation> {

}
