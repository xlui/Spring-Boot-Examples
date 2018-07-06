package me.xlui.example.repository;

import me.xlui.example.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findById(Long id);
}
