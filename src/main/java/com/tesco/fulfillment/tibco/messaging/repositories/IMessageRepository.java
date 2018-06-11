package com.tesco.fulfillment.tibco.messaging.repositories;

import javax.jms.JMSException;

import com.tesco.fulfillment.tibco.messaging.vo.MessageHeader;

public interface IMessageRepository {
	boolean publish(String message, MessageHeader messageHeader) throws JMSException;
}
