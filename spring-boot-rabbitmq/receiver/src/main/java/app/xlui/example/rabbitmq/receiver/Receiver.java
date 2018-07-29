package app.xlui.example.rabbitmq.receiver;

import app.xlui.example.rabbitmq.config.Config;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	@RabbitListener(queues = Config.queue)
	public void receiveMessage(String message) {
		System.out.println("Receive <" + message + ">");
	}
}
