package com.example.rest.webservices.restfulwebservices.users.posts.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.rest.webservices.restfulwebservices.exception.PostNotFoundException;
import com.example.rest.webservices.restfulwebservices.users.dao.UserRepository;
import com.example.rest.webservices.restfulwebservices.users.exception.UserNotFoundException;
import com.example.rest.webservices.restfulwebservices.users.model.User;
import com.example.rest.webservices.restfulwebservices.users.posts.dao.PostDao;
import com.example.rest.webservices.restfulwebservices.users.posts.dao.PostRepository;
import com.example.rest.webservices.restfulwebservices.users.posts.model.Post;
import com.example.rest.webservices.restfulwebservices.users.posts.model.UserPost;

@RestController
public class PostJpaResource {
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(path="/jpa/users/{userId}/posts")
	public List<UserPost> getUserAllPost(@PathVariable int userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if(!optionalUser.isPresent())
			throw new UserNotFoundException("User not exist for id:"+userId);
		return optionalUser.get().getPosts();
	}
	
	@GetMapping(path="/jpa/users/{userId}/posts/{postId}")
	public UserPost getUserAllPost(@PathVariable int userId, @PathVariable int postId) {
		Optional<UserPost> findUserPost = postRepository.findById(postId);
		if(!findUserPost.isPresent())
			throw new PostNotFoundException("Post not exist for post id:"+postId);
		return findUserPost.get();
	}
	
	@PostMapping("/jpa/users/{userId}/posts")
	public ResponseEntity<UserPost> createPost(@PathVariable int userId,@RequestBody UserPost post) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if(!optionalUser.isPresent())
			throw new UserNotFoundException("User not exist for id:"+userId);
		
		User user = optionalUser.get();
		post.setUser(user);
		UserPost savedPost = postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{userId}").buildAndExpand(savedPost.getPostId()).toUri();
		return ResponseEntity.created(location).body(savedPost);
	}

}