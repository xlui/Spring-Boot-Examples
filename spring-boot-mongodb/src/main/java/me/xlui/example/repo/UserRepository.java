package me.xlui.example.repo;

import me.xlui.example.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
    User findByUsername(String username);

    void deleteById(String id);
}