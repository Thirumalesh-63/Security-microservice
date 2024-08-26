package com.zapcom.Jwtsecurity.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zapcom.common.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

	Optional<User> findByEmail(String email);
	
	User findByName(String name);

}
