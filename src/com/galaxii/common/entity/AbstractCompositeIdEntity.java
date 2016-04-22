package com.galaxii.common.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class AbstractCompositeIdEntity
	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract Object getId();
	//public abstract void setId(Object id);

	public boolean equals(AbstractCompositeIdEntity other) {
		if (this == other) return true;

		if(getId() != other.getId()) {
			return false;
		}

		if(getId() == null) {
			return other.getId() == null;
		} else {
			return getId().equals(other.getId());
		}
	}

	public int hashCode() {
		return getId() == null ? 0 : getId().hashCode();
    }

	/*
	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}
	*/
}

