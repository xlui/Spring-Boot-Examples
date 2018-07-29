package app.xlui.example.jpa.repository;

import app.xlui.example.jpa.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
