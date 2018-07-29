package app.xlui.example.shiro.repository;

import app.xlui.example.shiro.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findById(Long id);
}
