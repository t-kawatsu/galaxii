package com.galaxii.common.hibernate.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.lang3.EnumUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see http://2lips.pl/blog/usertype-enumset-mysql-set-column/
 * @see http://appfuse.org/display/APF/Java+5+Enums+Persistence+with+Hibernate
 * @see http://www.gabiaxel.com/2011/01/better-enum-mapping-with-hibernate.html
 * @see http://www.intersult.com/wiki/page/Hibernate%20EnumSet
 * @see http://www.docjar.com/html/api/org/hibernate/type/EnumType.java.html
 */
public abstract class BitEnumSetUserType<E extends Enum<E>>
	implements UserType {

	protected static Logger logger = LoggerFactory.getLogger(BitEnumSetUserType.class);
    private static final int[] SQL_TYPES = {Types.BIT};
 
	private Class<? extends EnumSet<E>> clazz = null;
    private Class<E> enumClass;

    @SuppressWarnings("unchecked")
	protected BitEnumSetUserType(Class<E> c) {
        clazz = (Class<? extends EnumSet<E>>) EnumSet.noneOf(c).getClass();
        enumClass = c;
    }

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class<? extends EnumSet<E>> returnedClass() {
        return clazz;
    }
 
    @Override
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
		return cached;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable)value;
    }
 
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }
 
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y)
            return true;
        if (null == x || null == y)
            return false;
        return x.equals(y);
    }

    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }
 
    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }
 
    @Override
    public boolean isMutable() {
        return false;
    }
 
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        String name = rs.getString(names[0]);
        if (rs.wasNull()) {
			return null;
		}
        return EnumUtils.processBitVector(enumClass, Long.parseLong(name));
    }
 
    @Override
	@SuppressWarnings("unchecked")
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {
        if (null == value) {
            st.setNull(index, Types.BIT);
        } else {
        	Long sqlValue = null;
			// FIXME value != EnumSet ??
            //Set<E> values = (Set<E>)EnumSet.of((E)value);
        	if(value instanceof Set == false) {
        		sqlValue = EnumUtils.generateBitVector(enumClass, (E)value);
        	} else {
        		Set<E> values = (Set<E>)value;
        		if (!values.isEmpty()) {
        			sqlValue = EnumUtils.generateBitVector(enumClass, values);
        		}
        	}
            st.setLong(index, sqlValue);
        }
    }
 
}

