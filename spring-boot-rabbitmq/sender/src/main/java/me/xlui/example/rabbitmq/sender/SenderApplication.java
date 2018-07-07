package me.xlui.example.rabbitmq.sender;

import me.xlui.example.rabbitmq.config.Config;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SenderApplication implements CommandLineRunner {
	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public SenderApplication(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Bean
	public Queue queue() {
		return new Queue(Config.queue);
	}

	public static void main(String[] args) {
		SpringApplication.run(SenderApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		rabbitTemplate.convertAndSend(Config.queue, "Message From RabbitMQ~");
		rabbitTemplate.convertAndSend(Config.queue, "Current time: " + new Date());
		System.out.println("Successfully send message.");
	}
}
