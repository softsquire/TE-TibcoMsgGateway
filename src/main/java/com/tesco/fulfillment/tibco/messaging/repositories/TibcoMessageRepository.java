package com.tesco.fulfillment.tibco.messaging.repositories;

import java.net.ConnectException;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tesco.fulfillment.tibco.messaging.configuration.EventConfiguration;
import com.tesco.fulfillment.tibco.messaging.exceptions.TibcoConnectionException;
import com.tesco.fulfillment.tibco.messaging.vo.MessageHeader;
import com.tibco.tibjms.TibjmsTopicConnectionFactory;

@Slf4j
@Service
public class TibcoMessageRepository implements IMessageRepository {

	@Autowired
	private EventConfiguration eventConfiguration;
	
	
	@Override
	public boolean publish(String message, MessageHeader header) throws JMSException {
		boolean isSuccess = false;
		TibjmsTopicConnectionFactory factory = null;
		TopicConnection connection = null;
		TopicSession session = null;
		TopicPublisher publisher = null;

		try {
			log.info("Sending JMS message to tibco server : {} on topic::{} with message::{} and event header::{}", 
					eventConfiguration.getServerUrl(), header.getTopic(), message, header.toString());

			factory = new TibjmsTopicConnectionFactory(eventConfiguration.getServerUrl());
			factory.setConnAttemptCount(1);
			factory.setConnAttemptTimeout(eventConfiguration.getTimeOut());
			connection = factory.createTopicConnection(eventConfiguration.getUserName(),
					eventConfiguration.getPassword());

			session = connection.createTopicSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			Topic senderTopic = session.createTopic(header.getTopic());
			publisher = session.createPublisher(senderTopic);

			TextMessage jmsMessage = session.createTextMessage();
			jmsMessage.setText(message);

			jmsMessage.setStringProperty("eventType", header.getEventType());
			jmsMessage.setStringProperty("eventName", header.getEventName());
			jmsMessage.setStringProperty("eventVersion", header.getEventVersion());
			jmsMessage.setStringProperty("eventID", header.getEventID());
			
			publisher.publish(jmsMessage);

			log.info("Published message successfully to tibco server:{} with eventID = {} and eventName= {} and Message : {}",eventConfiguration.getServerUrl(), 
					header.getEventID(),header.getEventName(), message);
			isSuccess = true;
		} catch (JMSException ex) {
			if ((ex.getLinkedException() != null && ex.getLinkedException().getClass() == ConnectException.class)) {
				throw new TibcoConnectionException(ex.getMessage() + ex.getStackTrace(), ex.getErrorCode());
			}
			log.error("Publishing failed to TIBCO  for EventID ={} and eventName= {} and  Message :{} with Exception ::{}" , header.getEventID(),
					header.getEventName(), message, ex);
			throw ex;
		} 

		finally {
			if (connection != null)
				connection.close();
			if (publisher != null)
				publisher.close();
			if (session != null)
				session.close();
		}

		return isSuccess;
	}
	
}
