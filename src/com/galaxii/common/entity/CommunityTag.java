package com.galaxii.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table( name = "community_tags" )
public class CommunityTag extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CommunityTagId id;
	private Date createdAt;
	
	//private Integer count;

	@EmbeddedId
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public CommunityTagId getId() {
		return id;
	}

	public void setId(CommunityTagId id) {
		this.id = id;
	}

	@Column(name = "created_at")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
/*	
	//@Formula("count(*)")
	@Transient
	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}
	*/
}
