package com.galaxii.common.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.Request;

@Repository
@Transactional
public class RequestDao extends AbstractDao<Request> {

}
