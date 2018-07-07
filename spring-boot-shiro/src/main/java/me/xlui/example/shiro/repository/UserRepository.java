package me.xlui.example.shiro.repository;

import me.xlui.example.shiro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
