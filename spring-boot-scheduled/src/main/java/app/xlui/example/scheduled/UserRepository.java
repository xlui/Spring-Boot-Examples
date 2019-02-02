package app.xlui.example.scheduled;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query(value = "SELECT new java.lang.Boolean(COUNT(u) > 0) FROM t_user AS u " +
			"WHERE u.id = :id " +
			"AND u.expire < :date")
	Boolean isExpired(@Param("id") Integer id, @Param("date") LocalDate date);

	@Query("SELECT u FROM t_user AS u WHERE u.expire >= :date")
	List<User> findNotExpiredAt(@Param("date") LocalDate date);
}
