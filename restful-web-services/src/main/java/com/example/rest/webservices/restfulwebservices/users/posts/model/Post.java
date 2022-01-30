package com.example.rest.webservices.restfulwebservices.users.posts.model;

public class Post {

	private int userId;

	private int postId;

	private String description;

	public Post() {
	}

	public Post(int userId, int postId, String description) {
		super();
		this.userId = userId;
		this.postId = postId;
		this.description = description;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
}
