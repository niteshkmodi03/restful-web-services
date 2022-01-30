package com.example.rest.webservices.restfulwebservices.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3867953576448275082L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
