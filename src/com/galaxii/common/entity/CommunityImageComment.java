package com.galaxii.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table( name = "community_image_comments" )
public class CommunityImageComment extends AbstractEntity
	implements CommentEntity, UserViolationableEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer communityImageId;
	private Integer userId;
	private String description;
	private Integer likeCnt;
	private Date createdAt;
	private Date updatedAt;
	private Status status;
	
	private User user;
	private boolean isUserLiked;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "community_image_id")
	public Integer getCommunityImageId() {
		return communityImageId;
	}

	public void setCommunityImageId(Integer communityImageId) {
		this.communityImageId = communityImageId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "like_cnt")
	public Integer getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(Integer likeCnt) {
		this.likeCnt = likeCnt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Enumerated(EnumType.ORDINAL)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(
		name="user_id", 
		referencedColumnName="id", 
		insertable=false, updatable=false
	)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	@Transient
	public Integer getBaseId() {
		return this.communityImageId;
	}

	@Override
	public void setBaseId(Integer baseId) {
		this.communityImageId = baseId;
	}

	@Override
	@Transient
	public boolean getIsUserLiked() {
		return isUserLiked;
	}
	
	public void setIsUserLiked(boolean isUserLiked) {
		this.isUserLiked = isUserLiked;
	}
}
