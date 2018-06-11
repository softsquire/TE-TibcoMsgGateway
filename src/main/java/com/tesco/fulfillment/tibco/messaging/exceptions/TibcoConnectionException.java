package com.tesco.fulfillment.tibco.messaging.exceptions;

import lombok.ToString;

@ToString
public class TibcoConnectionException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private final String errorCode;

	public TibcoConnectionException(String reason, String errorCode) {
		super(reason);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
