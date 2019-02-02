package app.xlui.example.scheduled;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ScheduledApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;
	@Value("${scheduled.init}")
	private boolean init;

	public static void main(String[] args) {
		SpringApplication.run(ScheduledApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (init) init();
	}

	private void init() {
		System.out.println("Initializing db...");
		userRepository.deleteAll();

		var faker = Faker.instance();
		var from = faker.date().past(30, TimeUnit.DAYS);
		var to = faker.date().future(30, TimeUnit.DAYS);
		for (int i = 0; i < 50; i++) {
			var fakeExpire = faker.date().between(from, to);
			var expire = fakeExpire.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			var user = new User(expire);
			userRepository.save(user);
		}
		System.out.println("Success!");
	}
}

