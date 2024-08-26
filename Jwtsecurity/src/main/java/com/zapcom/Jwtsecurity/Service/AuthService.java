package com.zapcom.Jwtsecurity.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;


import com.zapcom.Jwtsecurity.Repository.UserRepository;
import com.zapcom.Jwtsecurity.model.CustomUserDetailsService;
import com.zapcom.common.model.User;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userData;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	
	@Autowired
	private JwtService jwtService;
	
	public String login(User user) {
		  UserDetails userdetails= customUserDetailsService.loadUserByUsername(user.getEmail());
		return jwtService.geneartejwttoken(userdetails);
	}

	public UserDetails validate(String token) {
		
	   String username=  jwtService.extractusername(token);
	  UserDetails userdetails= customUserDetailsService.loadUserByUsername(username);
	    jwtService.isTokenValid(token, userdetails);
	    return userdetails;
	     
	}

}
