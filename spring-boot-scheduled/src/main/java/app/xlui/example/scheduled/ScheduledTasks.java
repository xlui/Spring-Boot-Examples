package app.xlui.example.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
@EnableScheduling
public class ScheduledTasks {
	@Autowired
	private UserRepository userRepository;
	private static Map<User, Boolean> state = new HashMap<>();

	@Scheduled(cron = "*/10 * * * * *")
	public void checkExpire() {
		var users = userRepository.findNotExpiredAt(LocalDate.now());
		users.forEach(user -> state.put(user, false));
		System.out.println("Total not expired: " + users.size());
	}

	@Scheduled(fixedRate = 5000)
	public void sendNotification() {
		state.entrySet().stream()
				.filter(entry -> !entry.getValue())
				.map(Map.Entry::getKey)
				.forEach(key -> System.out.println("Send notification to user: " + key));
	}

	@Scheduled(fixedDelay = 3000)
	public void checkNotification() {
		state.entrySet().stream()
				// filter not notified user
				.filter(entry -> !entry.getValue())
				.map(Map.Entry::getKey)
				.forEach(key -> System.out.println("Have not send notification for user: " + key));
	}
}
