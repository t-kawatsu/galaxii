package com.galaxii.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.Inquiry;

@Repository
@Transactional
public class InquiryDao extends AbstractDao<Inquiry> {

	public List<Inquiry> fetchAll() {
		return resultList( getSession().createQuery("from Inquiry") );
	}

}
