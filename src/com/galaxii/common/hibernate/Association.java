package com.galaxii.common.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.sql.JoinType;

public class Association {
	private String associationPath;
	private JoinType joinType;
	private String alias;
	private Criterion withClause;
	public String getAssociationPath() {
		return associationPath;
	}
	public void setAssociationPath(String associationPath) {
		this.associationPath = associationPath;
	}
	public JoinType getJoinType() {
		return joinType;
	}
	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Criterion getWithClause() {
		return withClause;
	}
	public void setWithClause(Criterion withClause) {
		this.withClause = withClause;
	}
	
	public static Association join(
			String associationPath,
			JoinType joinType,
			String alias,
			Criterion withClause) {
		Association a = new Association();
		a.setAssociationPath(associationPath);
		a.setAlias(alias);
		a.setJoinType(joinType);
		a.setWithClause(withClause);
		return a;
	}
	
	public static Association join(
			String associationPath,
			JoinType joinType,
			String alias) {
		return Association.join(associationPath, joinType, alias, null);
	}
	
	public static Association join(
			String associationPath,
			JoinType joinType) {
		return Association.join(associationPath, joinType, null);
	}
}