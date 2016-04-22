package com.galaxii.common.entity;

import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table( name = "communities" )
public class Community extends AbstractEntity 
implements UserViolationableEntity, CommentableEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String title;
	private String description;
	private CommunityCategory categoryId;
	private Integer userId;
	private Integer commentCnt;
	private Integer communityUsersCnt;
	private Integer communityImageId;
	private Date createdAt;
	private Date updatedAt;
	private Status status;
	
	private User user;
	private List<CommunityTag> communityTags;
	private CommunityImage communityImage;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "category_id")
	@Enumerated(EnumType.ORDINAL)
	public CommunityCategory getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(CommunityCategory categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Override
	@Column(name = "comment_cnt")
	public Integer getCommentCnt() {
		return commentCnt;
	}

	@Override
	public void setCommentCnt(Integer commentCnt) {
		this.commentCnt = commentCnt;
	}

	@Column(name = "community_users_cnt")
	public Integer getCommunityUsersCnt() {
		return communityUsersCnt;
	}

	public void setCommunityUsersCnt(Integer communityUsersCnt) {
		this.communityUsersCnt = communityUsersCnt;
	}

	@Column(name = "community_image_id")
	public Integer getCommunityImageId() {
		return communityImageId;
	}

	public void setCommunityImageId(Integer communityImageId) {
		this.communityImageId = communityImageId;
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
	
	@OneToMany(fetch= FetchType.LAZY)
	@JoinColumn(
		name="community_id", 
		referencedColumnName="id", 
		insertable=false, updatable=false
	)
	public List<CommunityTag> getCommunityTags() {
		return communityTags;
	}
	
	public void setCommunityTags(List<CommunityTag> communityTags) {
		this.communityTags = communityTags;
	}
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(
		name="community_image_id", 
		referencedColumnName="id", 
		insertable=false, updatable=false
	)
	public CommunityImage getCommunityImage() {
		return communityImage;
	}
	
	public void setCommunityImage(CommunityImage communityImage) {
		this.communityImage = communityImage;
	}
}
