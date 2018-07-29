package app.xlui.example.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
	@KafkaListener(topics = {"kafka"})
	public void receiveMessage(String message) {
		System.out.println("Receive: [" + message + "]");
	}
}
