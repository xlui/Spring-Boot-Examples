package me.xlui.spring.repository;

import me.xlui.spring.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findById(Long id);
}
