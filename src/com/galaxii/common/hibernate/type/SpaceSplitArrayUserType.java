package com.galaxii.common.hibernate.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class SpaceSplitArrayUserType implements UserType {

    private static final int[] SQL_TYPES = {Types.VARCHAR};
	private static final char SEPARATOR = ' ';

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return List.class;
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
        return true;
    }
 
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        String name = rs.getString(names[0]);
        if (rs.wasNull()) {
			return null;
		}
        String[] values = StringUtils.split(name, SEPARATOR);
		return Arrays.asList(values);
    }
 
    @Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            String sqlValue = StringUtils.join((List)value, SEPARATOR);
            st.setString(index, sqlValue);
        }
    }
 
}

