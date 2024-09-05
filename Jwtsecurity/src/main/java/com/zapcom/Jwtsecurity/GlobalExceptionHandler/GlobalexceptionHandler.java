package com.zapcom.Jwtsecurity.GlobalExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@ControllerAdvice
public class GlobalexceptionHandler {

	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<String> handleSignatureException(SignatureException ex, WebRequest request) {
		return new ResponseEntity<>("Invalid JWT signature", HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
		return new ResponseEntity<>("JWT token has expired", HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UserAlreadyExists.class)
	public ResponseEntity<String> UserAlreadyExists(UserAlreadyExists ex, WebRequest request) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String> BadCredentialsException(BadCredentialsException ex, WebRequest request) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}
}
