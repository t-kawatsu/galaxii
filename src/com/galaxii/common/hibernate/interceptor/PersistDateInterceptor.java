package com.galaxii.common.hibernate.interceptor;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class PersistDateInterceptor extends EmptyInterceptor {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean onFlushDirty(Object entity,
            Serializable id,
            Object[] currentState,
            Object[] previousState,
            String[] propertyNames,
            Type[] types) {

    	for ( int i=0; i < propertyNames.length; i++ ) {
    		if ( "updatedAt".equals( propertyNames[i] ) ) {
    			currentState[i] = new Date();
    			return true;
    		}
    	}
    	return false;
    }

    public boolean onSave(Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {

    	boolean ret = false;
    	for ( int i=0; i<propertyNames.length; i++ ) {
    		if ( "createdAt".equals( propertyNames[i] ) || 
    			 "updatedAt".equals( propertyNames[i] ) ) {
    			state[i] = new Date();
    			ret = true;
    		}
    	}
    	return ret;
	}
}
