package com.example.rest.webservices.restfulwebservices.users.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.rest.webservices.restfulwebservices.users.model.User;

@Component
public class UserDao {
	
	private static List<User> userList = new ArrayList<>();
	
	private static int usersCount = 3;

	static {
		userList.add(new User(1, "Adam", new Date()));
		userList.add(new User(2, "Eva", new Date()));
		userList.add(new User(3, "Nik", new Date()));
	}
	
	public List<User> findAll(){
		return userList;
	}
	
	public User save(User user) {
		if(user.getId()==null) {
			user.setId(++usersCount);
		}
		userList.add(user);
		return user;
	}
   
	public User findOne(int id) {
		for(User user:userList) {
			if(user.getId()==id) {
				return user;
			}
		}
		return null;
	}
	
	public User delete(int id) {
//		Iterator<User> iterator = userList.iterator();
//		while(iterator.hasNext()) {
//			User user = iterator.next();
//			if(user.getId()==id) {
//				iterator.remove();
//				return user;
//			}
//		}
//		return null;
		for(User user:userList) {
			if(user.getId()==id) {
				userList.remove(user);
				return user;
			}
		}
		return null;
	}
	
}
