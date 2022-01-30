package com.example.rest.webservices.restfulwebservices.users.resource;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.rest.webservices.restfulwebservices.users.dao.UserDao;
import com.example.rest.webservices.restfulwebservices.users.exception.UserNotFoundException;
import com.example.rest.webservices.restfulwebservices.users.model.User;
import com.example.rest.webservices.restfulwebservices.users.posts.resource.PostResource;

@RestController
public class UserResource {

	@Autowired
	UserDao userDao;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	@GetMapping("/users/{id}")
	public EntityModel<User> getUser(@PathVariable int id) {
		User user = userDao.findOne(id);
		if(user==null) {
			throw new UserNotFoundException("user does not exist of id:"+id);
		}
		
		EntityModel<User> model = EntityModel.of(user);
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
		model.add(link.withRel("all-users"));
		
		WebMvcLinkBuilder postLink = linkTo(methodOn(PostResource.class).getUserAllPost(id));
		model.add(postLink.withRel("all-users-posts"));
		return model;
	}

	@PostMapping("/users")
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
		
		User savedUser =userDao.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable int id) {
		User user = userDao.delete(id);
		if(user==null) {
			throw new UserNotFoundException("user does not exist of id:"+id);
		}
		return ResponseEntity.noContent().build();
	}

}
