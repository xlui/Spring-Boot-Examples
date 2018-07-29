package app.xlui.example.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Date;

@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		kafkaTemplate.send("kafka", "hello kafka!");
		kafkaTemplate.send("kafka", "current time: " + new Date());
		System.out.println("Successfully send");
	}
}
