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
@Table( name = "community_activities" )
public class CommunityActivity extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer communityId;
	private CommunityContentsCategory communityContentsCategoryId;
	private Integer communityContentsId;
	private Integer userId;
	private String name;
	private Date createdAt;
	
	private User user;
	private Community community;
	
	private List<UserWatch> toUserWatches;
	private List<UserWatch> fromUserWatches;
	
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
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "community_contents_category_id")
	public CommunityContentsCategory getCommunityContentsCategoryId() {
		return communityContentsCategoryId;
	}
	public void setCommunityContentsCategoryId(
			CommunityContentsCategory communityContentsCategoryId) {
		this.communityContentsCategoryId = communityContentsCategoryId;
	}
	@Column(name = "community_contents_id")
	public Integer getCommunityContentsId() {
		return communityContentsId;
	}
	public void setCommunityContentsId(Integer communityContentsId) {
		this.communityContentsId = communityContentsId;
	}
	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
	
	@OneToMany(fetch= FetchType.LAZY)
	@JoinColumn(
		name="to_user_id", 
		referencedColumnName="user_id", 
		insertable=false, updatable=false
	)
	public List<UserWatch> getToUserWatches() {
		return toUserWatches;
	}
	public void setToUserWatches(List<UserWatch> toUserWatches) {
		this.toUserWatches = toUserWatches;
	}
	
	@OneToMany(fetch= FetchType.LAZY)
	@JoinColumn(
		name="from_user_id", 
		referencedColumnName="user_id", 
		insertable=false, updatable=false
	)
	public List<UserWatch> getFromUserWatches() {
		return fromUserWatches;
	}
	public void setFromUserWatches(List<UserWatch> fromUserWatches) {
		this.fromUserWatches = fromUserWatches;
	}
}
