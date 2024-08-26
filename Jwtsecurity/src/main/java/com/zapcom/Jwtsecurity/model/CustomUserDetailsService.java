package com.zapcom.Jwtsecurity.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zapcom.Jwtsecurity.Repository.UserRepository;
import com.zapcom.common.model.User;

public class CustomUserDetailsService implements UserDetailsService{
	
	
	@Autowired
	private UserRepository userRepository;

//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		User user=userRepository.findByEmail(email);
//		  System.err.println(user);
//		  return new CustomUserDetail(user);
//	}
//	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    User user = userRepository.findByEmail(email)
	        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
	    return new CustomUserDetails(user);
	}


}
