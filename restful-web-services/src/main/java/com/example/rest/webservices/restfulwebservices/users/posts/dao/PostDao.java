package com.example.rest.webservices.restfulwebservices.users.posts.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.rest.webservices.restfulwebservices.users.posts.model.Post;

@Component
public class PostDao {

	private static List<Post> posts = new ArrayList<>();

	static {
		posts.add(new Post(1,1,"cool pic"));
		posts.add(new Post(1,2,"sundayPose"));
		posts.add(new Post(2,1,"Awesome scene"));
		posts.add(new Post(2,2,"Hotty"));
		posts.add(new Post(3,1,"superb"));
		posts.add(new Post(3,2,"Tajmahal"));
	}

	public Post findUserPost(int userId, int postId) {
		Optional<Post> optionalPost = posts.stream().filter(p->p.getUserId()==userId && p.getPostId()==postId).findFirst();
		if(optionalPost.isPresent()) {
			return optionalPost.get();
		}
		return null;
	}
	
	public List<Post> findAllUserPost(int userId) {
		List<Post> postList = posts.stream().filter(p->p.getUserId()==userId).collect(Collectors.toList());
		return postList;
	}
	
	public Post createUserPost(int userId, Post post) {
		List<Post> postList = posts.stream().filter(p->p.getUserId()==userId).collect(Collectors.toList());
		post.setUserId(userId);
		post.setPostId(postList.size()+1);
		posts.add(post);
		return post;
	}

}
