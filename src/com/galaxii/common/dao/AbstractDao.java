package com.galaxii.common.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.galaxii.common.entity.AbstractEntity;
import com.galaxii.common.hibernate.Association;

abstract public class AbstractDao<T> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	protected SessionFactory sessionFactory;

	protected Class< T > entityClass;

	@SuppressWarnings("unchecked")
	public AbstractDao() {
        Type t = getClass().getGenericSuperclass();
		if(t instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) t;
			entityClass = (Class< T >) pt.getActualTypeArguments()[0];
		}
	}
	
	public Class<T> getEntityClass() {
		return entityClass;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public T findById(final Serializable id) {
		if(id == null) {
			return null;
		}
		return (T) getSession().get(entityClass, id);
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public int detectCountValue(Query q) {
		Iterator<Long> i = q.iterate();
		if(!i.hasNext()) {
			return 0;
		}
		return i.next().intValue();
	}

	@SuppressWarnings("unchecked")
	public int detectCountValue(Criteria c) {
		List<Long> ret = c.list();
		return ret != null && 0 < ret.size() ? ((Long) ret.get(0)).intValue() : 0;
	}

	@SuppressWarnings("unchecked")
	public List<T> resultList(Query q) {
		return q.list();
	}

	@Transactional
	public Serializable save(Object entity) {
		return getSession().save(entity);
	}
	
	@Transactional
	public void persist(Object entity) {
		getSession().persist(entity);
	}
	
	@Transactional
	public void update(Object entity) {
		getSession().update(entity);
	}

	@Transactional
	public void delete(Object entity) {
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
	}

	public <E extends AbstractEntity> List<Integer> detectIds(List<E> entities) {
		if(entities == null) {
			return null;
		}
		List<Integer> ret = new ArrayList<Integer>();
		for(E e :entities) {
			ret.add(e.getId());
		}
		return ret;
	}
	
	@Transactional
	public <LC> int count(
			List<Criterion> criterions,
			List<Association> associations) {
		List<Long> ret = find(
				Long.class, 
				criterions, 
				Projections.projectionList().add( Projections.rowCount() ), 
				associations, 
				null, null, null);
		return ret != null && 0 < ret.size() ? ((Long) ret.get(0)).intValue() : 0;
	}

	
	@Transactional
	public <LC> List<LC> find(
			Class < LC > clazz,
			List<Criterion> criterions, 
			ProjectionList projectionList, 
			List<Association> associations,
			Order order,
			Integer limit,
			Integer offset) {
		
		Session sess = getSession();
		
		Criteria criteria = sess.createCriteria(entityClass);
		if(projectionList != null) {
			criteria.setProjection(projectionList);
		}
		if(criterions != null) {
			for(Criterion c : criterions) {
				criteria.add(c);
			}
		}
		if(associations != null) {
			for(Association row : associations) {
				if(row.getWithClause() != null) {
					criteria.createCriteria(
							row.getAssociationPath(), 
							row.getAlias(), 
							row.getJoinType(), 
							row.getWithClause());
				} else if(row.getAlias() != null) {
					criteria.createCriteria(
							row.getAssociationPath(), 
							row.getAlias(), 
							row.getJoinType());
				} else {
					criteria.createCriteria(
							row.getAssociationPath(),
							row.getJoinType());
				}
			}
		}
		if(order != null) {
			criteria.addOrder(order);
		}
		if(limit != null) {
			criteria.setMaxResults(limit);
		}
		if(offset != null) {
			criteria.setFirstResult(offset);
		}
		@SuppressWarnings("unchecked")
		List<LC> rows = criteria.list();
		return rows;
	}
}
