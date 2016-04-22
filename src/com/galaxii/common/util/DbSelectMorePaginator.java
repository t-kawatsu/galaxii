package com.galaxii.common.util;

import java.util.List;

import com.galaxii.common.dao.AbstractDao;

public class DbSelectMorePaginator<T> extends DbSelectPaginator<T>
	implements MorePaginator<T> {
	
	private SessionUtil sessionUtil;
	private Integer id;

	public DbSelectMorePaginator(SessionUtil sessionUtil, AbstractDao<T> dao, Integer id) {
		super(dao);
		this.sessionUtil = sessionUtil;
		this.id = id;
	}
	
	private String detectPageKey() {
		return "page_" + dao.getEntityClass().getName() + "_" + id;
	}
	
	public DbSelectMorePaginator<T> clearPage() {
		sessionUtil.removeNamespace(detectPageKey());
		return this;
	}
	
	public Integer getPage() {
		Integer page = (Integer)sessionUtil.getNamespace(detectPageKey());
		return page == null ? 1 : (int)page;
	}
	
	public List<T> getItems() {
		List<T> items = super.getItems();
		sessionUtil.putNamespace(detectPageKey(), getPage()+1);
		return items;
	}
}
