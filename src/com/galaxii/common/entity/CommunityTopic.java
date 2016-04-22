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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table( name = "community_topics" )
public class CommunityTopic extends AbstractEntity
implements UserViolationableEntity, LikableEntity, CommentableEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer communityId;
	private Integer communityTopicImageId;
	private Integer userId;
	private String title;
	private String description;
	private Integer likeCnt;
	private Integer commentCnt;
	private Date createdAt;
	private Date updatedAt;
	private Status status;
	
	private boolean isUserLiked;
	
	private User user;
	private List<CommunityTopicImage> communityTopicImages;
	private CommunityTopicImage communityTopicImage;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "community_id")
	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	
	@Column(name = "community_topic_image_id")
	public Integer getCommunityTopicImageId() {
		return communityTopicImageId;
	}
	
	public void setCommunityTopicImageId(Integer communityTopicImageId) {
		this.communityTopicImageId = communityTopicImageId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	@Column(name = "like_cnt")
	public Integer getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(Integer likeCnt) {
		this.likeCnt = likeCnt;
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
		name="community_topic_id", 
		referencedColumnName="id", 
		insertable=false, updatable=false
	)
	public List<CommunityTopicImage> getCommunityTopicImages() {
		return communityTopicImages;
	}
	
	public void setCommunityTopicImages(List<CommunityTopicImage> communityTopicImages) {
		this.communityTopicImages = communityTopicImages;
	}
	
	@OneToOne(fetch= FetchType.LAZY)
	@JoinColumn(
		name="community_topic_image_id", 
		referencedColumnName="id", 
		insertable=false, updatable=false
	)
	public CommunityTopicImage getCommunityTopicImage() {
		return communityTopicImage;
	}
	
	public void setCommunityTopicImage(CommunityTopicImage communityTopicImage) {
		this.communityTopicImage = communityTopicImage;
	}

	@Override
	@Transient
	public boolean getIsUserLiked() {
		return isUserLiked;
	}

	@Override
	public void setIsUserLiked(boolean isUserLiked) {
		this.isUserLiked = isUserLiked;
	}
}
