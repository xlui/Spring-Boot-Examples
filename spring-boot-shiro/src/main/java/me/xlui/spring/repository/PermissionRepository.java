package me.xlui.spring.repository;

import me.xlui.spring.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
	Permission findById(Long id);
}
