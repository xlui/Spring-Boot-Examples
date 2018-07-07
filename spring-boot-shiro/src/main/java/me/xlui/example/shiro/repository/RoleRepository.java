package me.xlui.example.shiro.repository;

import me.xlui.example.shiro.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findById(Long id);
}
