package com.example.rest.webservices.restfulwebservices.users.posts.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.rest.webservices.restfulwebservices.exception.PostNotFoundException;
import com.example.rest.webservices.restfulwebservices.users.exception.UserNotFoundException;
import com.example.rest.webservices.restfulwebservices.users.posts.dao.PostDao;
import com.example.rest.webservices.restfulwebservices.users.posts.model.Post;

@RestController
public class PostResource {
	
	@Autowired
	PostDao postDao;
	
	@GetMapping(path="/users/{userId}/posts")
	public List<Post> getUserAllPost(@PathVariable int userId) {
		List<Post> findAllUserPost = postDao.findAllUserPost(userId);
		if(findAllUserPost==null || findAllUserPost.isEmpty())
			throw new UserNotFoundException("User not exist for id:"+userId);
		return findAllUserPost;
	}
	
	@GetMapping(path="/users/{userId}/posts/{postId}")
	public Post getUserAllPost(@PathVariable int userId, @PathVariable int postId) {
		Post findUserPost = postDao.findUserPost(userId, postId);
		if(findUserPost==null)
			throw new PostNotFoundException("Post not exist for post id:"+postId);
		return findUserPost;
	}
	
	@PostMapping("/users/{userId}/posts")
	public ResponseEntity<Post> createPost(@PathVariable int userId,@RequestBody Post post) {
		Post savedPost = postDao.createUserPost(userId,post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{userId}").buildAndExpand(savedPost.getUserId()).toUri();
		return ResponseEntity.created(location).body(savedPost);
	}

}