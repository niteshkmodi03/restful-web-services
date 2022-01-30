package com.example.rest.webservices.restfulwebservices.users.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.rest.webservices.restfulwebservices.users.dao.UserRepository;
import com.example.rest.webservices.restfulwebservices.users.exception.UserNotFoundException;
import com.example.rest.webservices.restfulwebservices.users.model.User;
import com.example.rest.webservices.restfulwebservices.users.posts.resource.PostResource;

@RestController
public class UserJpaResource {

//	@Autowired
//	UserDao userDao;
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("/jpa/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> getUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("user does not exist of id:"+id);
		}
		
		EntityModel<User> model = EntityModel.of(user.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
		model.add(link.withRel("all-users"));
		
		WebMvcLinkBuilder postLink = linkTo(methodOn(PostResource.class).getUserAllPost(id));
		model.add(postLink.withRel("all-users-posts"));
		return model;
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
		
		User savedUser =userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/jpa/users/{id}")
	public ResponseEntity<User> updateUserEmail(@PathVariable Integer id,@RequestBody String email) {
		
		Optional<User> optionalUser=userRepository.findById(id);
		if(!optionalUser.isPresent()) {
			throw new UserNotFoundException("User does not exist");
		}
		User user = optionalUser.get();
		user.setEmail(email.trim());
		System.out.println("updated user with email:"+email+"Email:"+user.getEmail());
		User savedUser =userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).body(user);
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("user does not exist of id:"+id);
		}
		userRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
