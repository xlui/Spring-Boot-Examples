package me.xlui.spring;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
	User findByUsername(String username);

	void deleteById(String id);
}