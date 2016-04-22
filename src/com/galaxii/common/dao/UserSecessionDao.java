package com.galaxii.common.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.UserSecession;

@Transactional
@Repository
public class UserSecessionDao extends AbstractDao<UserSecession> {

}
