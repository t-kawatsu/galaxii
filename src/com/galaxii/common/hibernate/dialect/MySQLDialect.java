package com.galaxii.common.hibernate.dialect;

import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

import com.galaxii.common.hibernate.dialect.function.BitFlagFunction;
import com.galaxii.common.hibernate.dialect.function.MatchAgainstFunction;

public class MySQLDialect 
	extends org.hibernate.dialect.MySQLDialect {

	public MySQLDialect() {
		super();
		registerFunction( "match_against", new MatchAgainstFunction());
		registerFunction( "bitflag", new BitFlagFunction());
		registerFunction( "find_in_set", new StandardSQLFunction( "find_in_set", StandardBasicTypes.INTEGER ) );
		//registerFunction("match_against", new SQLFunctionTemplate(Hibernate.BOOLEAN, "match ?1 against (?2 in boolean mode)") );
	}
}
