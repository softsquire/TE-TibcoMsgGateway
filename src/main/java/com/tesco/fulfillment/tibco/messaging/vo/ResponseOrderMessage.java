package com.tesco.fulfillment.tibco.messaging.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ResponseOrderMessage {	

	private String message;
	private String statusCode;
	
}
