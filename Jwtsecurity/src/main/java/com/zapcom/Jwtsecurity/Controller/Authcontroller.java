package com.zapcom.Jwtsecurity.Controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping("/user")
	public ResponseEntity<Map<String, String>>  CreateUser(@RequestBody User user) {
		Map<String, String> m=authService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(m);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody User user) {

			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
			if (authenticate.isAuthenticated()) {
				Map<String, String> m= authService.login(user); // Ensure this method handles the authenticated user
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(m);
			}
		     throw new RuntimeException("User not found or credentials are incorrect");
	}

	@GetMapping("/validate")
	public ResponseEntity<Map<String, Object>> validate(@RequestParam("token") String token) {
		UserDetails userDetails = authService.validate(token);
		System.err.println(userDetails);
		if (userDetails == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Collections.singletonMap("message", "Invalid token"));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("username", userDetails.getUsername());
		response.put("roles",
				userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

		return ResponseEntity.ok(response);
	}

}
