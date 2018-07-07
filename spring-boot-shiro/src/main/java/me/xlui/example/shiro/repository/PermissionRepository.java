package me.xlui.example.shiro.repository;

import me.xlui.example.shiro.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findById(Long id);
}
