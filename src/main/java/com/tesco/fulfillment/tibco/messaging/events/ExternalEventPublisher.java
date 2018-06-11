package com.tesco.fulfillment.tibco.messaging.events;

import java.util.Optional;

import javax.jms.JMSException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tesco.fulfillment.tibco.messaging.configuration.EventConfiguration;
import com.tesco.fulfillment.tibco.messaging.exceptions.TibcoConnectionException;
import com.tesco.fulfillment.tibco.messaging.repositories.IMessageRepository;
import com.tesco.fulfillment.tibco.messaging.utils.RetryServiceCommand;

@Slf4j
@Component
public class ExternalEventPublisher {
	
	@Autowired
	private EventConfiguration eventConfiguration;
	
	@Autowired
	private IMessageRepository messageRepository;
	
	public boolean publishMessage(GenericExternalEvent<String> genericExternalEvent)
			throws JMSException, InterruptedException, TibcoConnectionException {

		RetryServiceCommand<Boolean> publishCommand = new RetryServiceCommand<Boolean>(
				genericExternalEvent.getHeader().getEventType(), eventConfiguration.getRetryDelay(), eventConfiguration.getMaxRetryCount());
		Optional<Boolean> response = publishCommand.retry(() -> {
			boolean status = false;
			try {
				status = messageRepository.publish(genericExternalEvent.getPayload(),
						genericExternalEvent.getHeader());
			}catch (TibcoConnectionException | JMSException ex) {
				log.error("ExternalEventPublisher-->Failed to publish message to tibco server due to : {}", ex);
			}
			return status;
		});
		return response.get();
	
	}
}
