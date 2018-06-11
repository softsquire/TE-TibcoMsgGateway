package com.tesco.fulfillment.tibco.messaging.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonProperty;

@NoArgsConstructor
@Getter
@ToString
public class FulfillmentNotificationRequest {
	
	@JsonProperty("messageHeader")
	private MessageHeader eventMessageHeader;
	
	@JsonProperty("messageBody")
	private MessagePayload messagePayload;
		
}
