package com.tesco.fulfillment.tibco.messaging.exceptions;

import lombok.ToString;

@ToString
public class MarshalingEventException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private final String storeId;
	
	public MarshalingEventException(String storeId, String message ) {
		super(message);
		this.storeId = storeId;
	}
	
	public String getStoreId() {
		return storeId;
	}

}
