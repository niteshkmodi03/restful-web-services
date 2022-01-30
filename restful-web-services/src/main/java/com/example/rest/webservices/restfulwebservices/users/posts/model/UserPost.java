package com.example.rest.webservices.restfulwebservices.users.posts.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.example.rest.webservices.restfulwebservices.users.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserPost {

	@Id
	@GeneratedValue
	private int postId;

	@Size(min=2,max=100,message="Post size is not validated")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;

	public UserPost() {
	}

	public UserPost(int postId, String description) {
		super();
		this.postId = postId;
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "UserPost [postId=" + postId + ", description=" + description + "]";
	}
	
	
}
