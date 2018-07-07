package me.xlui.example.mongodb.repo;

import me.xlui.example.mongodb.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
    User findByUsername(String username);

    void deleteById(String id);
}