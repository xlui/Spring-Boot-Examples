package me.xlui.spring.repo;

import me.xlui.spring.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
    User findByUsername(String username);

    void deleteById(String id);
}