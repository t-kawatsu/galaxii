package com.galaxii.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table( name = "user_informations" )
public class UserInformation extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer toUserId;
	private Integer fromUserId;
	private UserInformationContentsCategory userInformationContentsCategoryId;
	private Integer userInformationContentsId;
	private String name;
	private UserInformationStatus status;
	private Date createdAt;
	private User fromUser;
	private User toUser;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="to_user_id")
	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	@Column(name="from_user_id")
	public Integer getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}

	@Column(name="user_information_contents_category_id")
	public UserInformationContentsCategory getUserInformationContentsCategoryId() {
		return userInformationContentsCategoryId;
	}

	public void setUserInformationContentsCategoryId(
			UserInformationContentsCategory userInformationContentsCategoryId) {
		this.userInformationContentsCategoryId = userInformationContentsCategoryId;
	}

	@Column(name="user_information_contents_id")
	public Integer getUserInformationContentsId() {
		return userInformationContentsId;
	}

	public void setUserInformationContentsId(Integer userInformationContentsId) {
		this.userInformationContentsId = userInformationContentsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserInformationStatus getStatus() {
		return status;
	}

	public void setStatus(UserInformationStatus status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	@ManyToOne(fetch= FetchType.LAZY)
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
	
	@ManyToOne(fetch= FetchType.LAZY)
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
