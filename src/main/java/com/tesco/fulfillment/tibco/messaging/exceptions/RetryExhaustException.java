package com.tesco.fulfillment.tibco.messaging.exceptions;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class RetryExhaustException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private final String storeId;

	public RetryExhaustException(String storeId, String message) {
		super(message);
		this.storeId = storeId;
	}

}
