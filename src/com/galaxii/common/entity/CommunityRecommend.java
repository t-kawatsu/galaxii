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
@Table( name = "community_recommends" )
public class CommunityRecommend extends AbstractEntity
implements UserViolationableEntity, CommentableEntity, LikableEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer communityId;
	private Integer recommendCommunityId;
	private RecommendType type;
	private String url;
	private String title;
	private String description;
	private Integer userId;
	private String imagePath;
	private Integer likeCnt;
	private Integer commentCnt;
	private Date createdAt;
	private Date updatedAt;
	private Status status;
	
	private User user;
	private Community community;
	private Community recommendCommunity;
	private boolean isUserLiked;

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
	
	@Column(name = "recommend_community_id")
	public Integer getRecommendCommunityId() {
		return recommendCommunityId;
	}

	public void setRecommendCommunityId(Integer recommendCommunityId) {
		this.recommendCommunityId = recommendCommunityId;
	}

	public RecommendType getType() {
		return type;
	}

	public void setType(RecommendType type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "image_path")
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(
		name="community_id", 
		referencedColumnName="id", 
		insertable=false, updatable=false
	)
	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(
		name="recommend_community_id", 
		referencedColumnName="id", 
		insertable=false, updatable=false
	)
	public Community getRecommendCommunity() {
		return recommendCommunity;
	}

	public void setRecommendCommunity(Community recommendCommunity) {
		this.recommendCommunity = recommendCommunity;
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
