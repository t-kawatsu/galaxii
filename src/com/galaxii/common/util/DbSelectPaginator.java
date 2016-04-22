package com.galaxii.common.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;

import com.galaxii.common.dao.AbstractDao;
import com.galaxii.common.hibernate.Association;

public class DbSelectPaginator<T> 
	implements Paginator<T> {
	
	protected AbstractDao<T> dao;
	
	protected int limit;
	protected int page;
	protected List<Criterion> criterions;
	protected ProjectionList projectionList;
	protected List<Association> associations;
	protected Order order;
	
	public DbSelectPaginator(AbstractDao<T> dao) {
		this.dao = dao;
	}
	
	public void setProjections( ProjectionList projectionList ) {
		this.projectionList = projectionList;
	}
	
	public void addCriterion( Criterion criterion ) {
		if(criterions == null) {
			criterions = new ArrayList<Criterion>();
		}
		criterions.add(criterion);
	}
	
	public void addAssociation(Association association) {
		if(associations == null) {
			associations = new ArrayList<Association>();
		}
		associations.add(association);
	}
	
	public void addOrder(Order order) {
		this.order = order;
	}
	
	public int getTotal() {
		return dao.count(criterions, associations);
	}
	
	public List<T> getItems() {
		List<T> rows = dao.find(
				dao.getEntityClass(), criterions, null, associations, order, getLimit(), (getPage()-1) * getLimit());
		return rows;
	}
	
	public Integer getPage() {
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page == null ? 1 : (int)page;
	}
	
	public Integer getLimit() {
		return limit;
	}
	
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	public SimplePager<T> paginate() {	
		int total = getTotal();
		if(total == 0) {
			return new SimplePager<T>(getLimit(), getPage(), null, total);
		}
		return new SimplePager<T>(getLimit(), getPage(), getItems(), total);
	}
}
