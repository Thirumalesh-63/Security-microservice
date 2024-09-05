package com.zapcom.Jwtsecurity.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zapcom.Jwtsecurity.GlobalExceptionHandler.UserAlreadyExists;
import com.zapcom.Jwtsecurity.Repository.UserRepository;
import com.zapcom.Jwtsecurity.model.CustomUserDetailsService;
import com.zapcom.common.model.DatabaseSequence;
import com.zapcom.common.model.User;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private JwtService jwtService;

	public Map<String, String> createUser(User user) {
		int id = generateSequence("user-sequence");
		user.setId(id);
		Optional<User> testUser = userRepository.findByEmail(user.getEmail());

		if (testUser.isPresent()) {
			throw new UserAlreadyExists("user already exists with the email id " + user.getEmail());
		}
		   userRepository.save(user);
		UserDetails userdetails = customUserDetailsService.loadUserByUsername(user.getEmail());
		String jwt=jwtService.geneartejwttoken(userdetails);
		Map<String, String> hashmap=new HashMap<>();
		hashmap.put("token", jwt);
		hashmap.put("role", userdetails.getAuthorities().toString());
		return hashmap;

	}

	@Transactional
	public int generateSequence(String seqName) {
		Query query = Query.query(Criteria.where("_id").is(seqName));
		Update update = new Update().inc("seq", 1);

		DatabaseSequence counter = mongoTemplate.findAndModify(query, update,
				FindAndModifyOptions.options().returnNew(true).upsert(true), // Ensure upsert and return the new
																				// document after the update
				DatabaseSequence.class);
		if (counter == null) {
			System.err.println(counter.getSeq());
			counter = new DatabaseSequence();
			counter.setId(seqName);
			counter.setSeq(1);
			mongoTemplate.save(counter);
			return 1;
		}

		return counter != null ? (int) counter.getSeq() : 1;
	}

	public Map<String, String> login(User user) {
		UserDetails userdetails = customUserDetailsService.loadUserByUsername(user.getEmail());
		
		String jwt=jwtService.geneartejwttoken(userdetails);
		User user2=userRepository.findByEmail(user.getEmail()).get();
		Map<String, String> hashmap=new HashMap<>();
		hashmap.put("id", String.valueOf(user2.getId()));
		hashmap.put("email", user2.getEmail());
		hashmap.put("token", jwt);
		hashmap.put("role", userdetails.getAuthorities().toString());
		return hashmap;
	}

	public UserDetails validate(String token) {
		String username = jwtService.extractusername(token);
		UserDetails userdetails = customUserDetailsService.loadUserByUsername(username);
		jwtService.isTokenValid(token, userdetails);
		return userdetails;

	}

}
