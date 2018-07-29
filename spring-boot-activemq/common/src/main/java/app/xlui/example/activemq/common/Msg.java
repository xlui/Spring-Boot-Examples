package app.xlui.example.activemq.common;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class Msg implements MessageCreator {
	@Override
	public Message createMessage(Session session) throws JMSException {
		return session.createTextMessage("Test Message");
	}
}
