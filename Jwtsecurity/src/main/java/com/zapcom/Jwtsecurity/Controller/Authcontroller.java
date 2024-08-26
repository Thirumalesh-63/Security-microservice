package com.zapcom.Jwtsecurity.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.zapcom.Jwtsecurity.Service.AuthService;
import com.zapcom.common.model.User;

@RestController
@RequestMapping("auth")
public class Authcontroller {


	@Autowired
	private AuthService authService;


	@Autowired
	private AuthenticationManager authenticationManager;


	@PostMapping("/login")
	public String login(@RequestBody User user) {
	    try {
	        Authentication authenticate = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
	        );
	        System.err.println("Authenticated: " + authenticate.isAuthenticated());

	        if (authenticate.isAuthenticated()) {
	            return authService.login(user); // Ensure this method handles the authenticated user
	        }
	    } catch (BadCredentialsException e) {
	        System.err.println("Invalid credentials: " + e.getMessage());
	    }

	    throw new RuntimeException("User not found or credentials are incorrect");
	}


	@PostMapping("/validate")
	public UserDetails validate(@RequestParam("token") String token) {
		return authService.validate(token);
		
	}


}
