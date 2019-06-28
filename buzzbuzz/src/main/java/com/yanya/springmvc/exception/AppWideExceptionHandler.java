package com.yanya.springmvc.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
public class AppWideExceptionHandler extends DefaultHandlerExceptionResolver{
	
	@ExceptionHandler(PageNotFoundException.class)
	public String pageNotFoundHandler(){
		return "error";
	}
	
	public String handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request, HttpServletResponse response){
		return "error";
	}
	
	@ExceptionHandler(UserNotAuthenticatedException.class)
	public String userNotAuthenticatedHandler(){
		return "error";
	}
	
	
	@ExceptionHandler(UserTokenInvalidException.class)
	public String userTokenInvalidHandler(){
		return "error";
	}
	
	@ExceptionHandler(SessionExpiredException.class)
	public String sessionExpiredHandler(){
		return "error";
	}
	@ExceptionHandler(InternalServerErrorException.class)
	public String internalServerErrorException(){
		return "error";
	}

	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String noHandlerFound(){
		return "error";
	}
}