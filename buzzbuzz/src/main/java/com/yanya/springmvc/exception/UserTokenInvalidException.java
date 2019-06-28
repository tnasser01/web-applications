package com.yanya.springmvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN,
				reason="User Token invalid")
public class UserTokenInvalidException extends RuntimeException {
	
	
	
}