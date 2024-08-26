package com.zapcom.Jwtsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.zapcom.Jwtsecurity.model.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class Authconfig {
//	
	@Bean
	public UserDetailsService userDetailsService() {
		 return new CustomUserDetailsService();
		
	}
	
	@Bean
	public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception {
		
		http
		.csrf()
		.disable()
		.authorizeRequests()
		.requestMatchers("/auth/login","/auth/valiate").permitAll();	
		return http.build();
		
	}
	
	 @Bean
	   public AuthenticationProvider authenticationprovider() {
		   DaoAuthenticationProvider authprovider=new DaoAuthenticationProvider();
		    authprovider.setUserDetailsService(userDetailsService());
		    authprovider.setPasswordEncoder(passwordEncoder());
		    return authprovider;
	   }
	   @Bean
	   public AuthenticationManager authenticationmanager(AuthenticationConfiguration config) throws Exception {
		   return config.getAuthenticationManager();
	   }
	   
	   @Bean
	   public PasswordEncoder passwordEncoder() {
		
		   return NoOpPasswordEncoder.getInstance();	
		   }
	   
	   @Bean
	   public CustomUserDetailsService customUserDetailsService() {
		   return new CustomUserDetailsService();
	   }

}
