package com.yanya.springmvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED,
				reason="User Not Authenticated")
public class UserNotAuthenticatedException extends ClassCastException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
}