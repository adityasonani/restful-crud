package com.aditya.mobilews.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aditya.mobilews.ui.model.response.ErrorMessage;

@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value= {Exception.class}) 
	public ResponseEntity<Object> handleAnyException(Exception e, WebRequest request) {
		
		String errorDesc = e.getLocalizedMessage();
		
		if (errorDesc==null) errorDesc=e.toString();

		ErrorMessage error = new ErrorMessage(new Date(), errorDesc);
		
		return new ResponseEntity<Object> (error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
