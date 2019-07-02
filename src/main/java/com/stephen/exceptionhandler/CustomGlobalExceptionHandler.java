/**
 * Reference: https://www.mkyong.com/spring-boot/spring-rest-hello-world-example/
 * Reference: https://www.mkyong.com/spring-boot/spring-rest-validation-example/
 * Reference: https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
 */
package com.stephen.exceptionhandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.stephen.exception.BookNotFoundException;
import com.stephen.exception.BookUnSupportedFieldPatchException;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messages;
	
    // Let Spring handle the exception, we just override the status code
//    @ExceptionHandler(BookNotFoundException.class)
//    public void springHandleNotFound(HttpServletResponse response) throws IOException {
//        response.sendError(HttpStatus.NOT_FOUND.value());
//    }
	
	//reference: https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> springHandleNotFound(RuntimeException ex, WebRequest request) throws IOException {
    	Map<String, Object> body = new LinkedHashMap<>();
    	body.put("timestamp", new Date());
    	body.put("status", HttpStatus.NOT_FOUND.value());
    	body.put("errors", Arrays.asList(ex.getMessage()));

    	return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
	//Reference: https://www.bealdung.com/spring-security-registration-verification-email
//    @ExceptionHandler(BookNotFoundException.class)
//    public ResponseEntity<Object> springHandleNotFound(RuntimeException ex, WebRequest request) throws IOException {
//    	logger.info("404 Status Code", ex);
//    	Map<String, Object> body = new LinkedHashMap<>();
//    	body.put("timestamp", new Date());
//    	body.put("status", HttpStatus.NOT_FOUND.value());
//    	body.put("errors", Arrays.asList(ex.getMessage()));
//
//    	return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request); //this would cause exception logged in console
//    }

    @ExceptionHandler(BookUnSupportedFieldPatchException.class)
    public void springUnSupportedFieldPatch(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.METHOD_NOT_ALLOWED.value());
    }
    
    // error handling for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
    		MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	Map<String, Object> body = new LinkedHashMap<>();
    	body.put("timestamp", new Date());
    	body.put("status", status.value());
    	
    	//Get all errors
    	List<String> errors = ex.getBindingResult()
    			.getFieldErrors()
    			.stream()
    			.map(x -> x.getDefaultMessage())
    			.collect(Collectors.toList());
    	
    	body.put("errors", errors);
    	
    	return new ResponseEntity<>(body, headers, status);
    }
    
    //Reference: https://www.bealdung.com/spring-security-registration-verification-email
    @ExceptionHandler(ConstraintViolationException.class)
//    public void constrainViolationException(HttpServletResponse response) throws IOException {
//    	response.sendError(HttpStatus.BAD_REQUEST.value());
//    }
    public ResponseEntity<Object> constrainViolationException(RuntimeException ex, WebRequest request) {
    	Map<String, Object> body = new LinkedHashMap<>();
    	body.put("timestamp", new Date());
    	body.put("status", HttpStatus.BAD_REQUEST.value());
    	body.put("errors", Arrays.asList(ex.getMessage()));

    	return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
