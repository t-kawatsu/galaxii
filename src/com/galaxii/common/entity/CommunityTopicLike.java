package com.galaxii.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table( name = "community_topic_likes" )
public class CommunityTopicLike extends AbstractCompositeIdEntity 
implements LikeEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CommunityTopicLikeId id;
	private Date createdAt;
	private Date updatedAt;
	
	private User user;

	@EmbeddedId
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public CommunityTopicLikeId getId() {
		return id;
	}

	public void setId(CommunityTopicLikeId id) {
		this.id = id;
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
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(
		name="user_id", 
		referencedColumnName="id", 
		insertable=false, updatable=false
	)
	@Override
	public User getUser() {
		return user;
	}
	@Override
	public void setUser(User user) {
		this.user = user;
	}
}
