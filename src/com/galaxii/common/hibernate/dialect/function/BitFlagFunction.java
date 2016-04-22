package com.galaxii.common.hibernate.dialect.function;

import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

public class BitFlagFunction
	implements SQLFunction {

	@Override
	public Type getReturnType(Type arg0, Mapping arg1) throws QueryException {
		return StandardBasicTypes.INTEGER;
	}

	@Override
	public boolean hasArguments() {
		return true;
	}

	@Override
	public boolean hasParenthesesIfNoArguments() {
		return false;
	}

	@Override
	public String render(Type arg0, @SuppressWarnings("rawtypes") List arg1, SessionFactoryImplementor arg2)
			throws QueryException {
		if(arg1.size() != 2) {
			return "";
		}
		return "(" + arg1.get(0) + " & " + arg1.get(1) + ")";
	}
}
