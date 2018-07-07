package me.xlui.example.jpa.repository;

import me.xlui.example.jpa.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
