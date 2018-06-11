package com.tesco.fulfillment.tibco.messaging.exceptions;

import lombok.ToString;

@ToString
public class MessagePayloadValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MessagePayloadValidationException(String message){
		super(message);
	}

}
