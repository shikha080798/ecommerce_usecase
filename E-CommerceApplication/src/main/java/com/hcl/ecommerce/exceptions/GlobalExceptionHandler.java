package com.hcl.ecommerce.exceptions;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonParseException;
import com.hcl.ecommerce.ApiStatusCode;


@RestControllerAdvice
public class GlobalExceptionHandler extends Exception {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 4313385015068096311L;
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> exceptionHandling(Exception exception,WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setStatusCode(500);
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ValidationExceptionHandler.class)
	public ResponseEntity<ErrorDetails> exceptionHandling(ValidationExceptionHandler exception){
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setStatusCode(ApiStatusCode.INVALID_DATA);
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.OK);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorDetails> exceptionHandling(ProductNotFoundException exception){
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setStatusCode(ApiStatusCode.PRODUCT_NOT_FOUND);
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.OK);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> exceptionHandling(MethodArgumentNotValidException exception){
		ErrorResponse errorDetails = new ErrorResponse();
		List<FieldError> list = exception.getBindingResult().getFieldErrors();
		for(FieldError errorDetail:list) {
			errorDetails.getErrorMaps().put(errorDetail.getField(), errorDetail.getDefaultMessage());
		}
		return new ResponseEntity<ErrorResponse>(errorDetails,HttpStatus.OK);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorDetails> exceptionHandling(ConstraintViolationException exception){
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setStatusCode(ApiStatusCode.VALIDATION_FAILED);
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorDetails> exceptionHandling(HttpMessageNotReadableException exception){
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setMessage("Enter data in valid format");
		errorDetails.setStatusCode(ApiStatusCode.VALIDATION_FAILED);
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
	}

}
