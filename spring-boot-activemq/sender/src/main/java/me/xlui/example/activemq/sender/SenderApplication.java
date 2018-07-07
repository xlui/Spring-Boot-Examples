package me.xlui.example.activemq.sender;

import me.xlui.example.activemq.common.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class SenderApplication implements CommandLineRunner {
	private final JmsTemplate jmsTemplate;

	@Autowired
	public SenderApplication(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(SenderApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		jmsTemplate.send("my-destination", new Msg());
		System.out.println("Send Success!");
	}
}
