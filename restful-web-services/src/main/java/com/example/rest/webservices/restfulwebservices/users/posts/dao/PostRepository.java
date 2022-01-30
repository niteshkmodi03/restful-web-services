package com.example.rest.webservices.restfulwebservices.users.posts.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rest.webservices.restfulwebservices.users.posts.model.UserPost;

@Repository
public interface PostRepository extends JpaRepository<UserPost, Integer> {

}
