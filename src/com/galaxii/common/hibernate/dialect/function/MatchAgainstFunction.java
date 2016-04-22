package com.galaxii.common.hibernate.dialect.function;

import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

/**
 * https://groups.google.com/forum/?fromgroups=#!topic/java-generic-dao/15a-qULr7IY
 */
public class MatchAgainstFunction
	implements SQLFunction {

	public boolean hasArguments() {
		return true;
	}

	public boolean hasParenthesesIfNoArguments() {
		return false;
	}

	public Type getReturnType(Type firstArgumentType, Mapping mapping) 
		throws QueryException {
		return null;
		//return new BooleanType();
	}

	public String render(Type firstArgumentType, @SuppressWarnings("rawtypes") List args, SessionFactoryImplementor factory) 
		throws QueryException {
		if(args.size() != 2) {
			return "";
		}
		return "MATCH(" + args.get(0) + ") AGAINST(" + args.get(1) + " in boolean mode)"; 
	}
}
