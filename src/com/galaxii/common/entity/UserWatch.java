package com.galaxii.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table( name = "user_watches" )
public class UserWatch extends AbstractCompositeIdEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserWatchId id;
	private Date createdAt;
	
	private User fromUser;
	private User toUser;

	@EmbeddedId
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public UserWatchId getId() {
		return id;
	}
	public void setId(UserWatchId id) {
		this.id = id;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	@OneToOne(fetch= FetchType.LAZY)
	@JoinColumn(
		name="from_user_id", 
		referencedColumnName="id", 
		insertable=false, updatable=false
	)
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	
	@OneToOne(fetch= FetchType.LAZY)
	@JoinColumn(
		name="to_user_id", 
		referencedColumnName="id", 
		insertable=false, updatable=false
	)
	public User getToUser() {
		return toUser;
	}
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
}
