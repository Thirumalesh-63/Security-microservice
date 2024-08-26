package com.zapcom.Jwtsecurity.GlobalExceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;

import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalexceptionHandler {
	
	 // Handle JWT SignatureException
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureException(SignatureException ex, WebRequest request) {
        // Create a custom response object if needed
        RuntimeException errorResponse = new RuntimeException( "Invalid JWT signature");
        
        // Return a ResponseEntity with the error details and status code
        return new ResponseEntity<>(errorResponse.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
