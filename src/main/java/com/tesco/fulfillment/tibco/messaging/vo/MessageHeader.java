package com.tesco.fulfillment.tibco.messaging.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonProperty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class MessageHeader {
	
	@JsonProperty("eventType")
	private String eventType;
	
	@JsonProperty("eventName")
	private String eventName;
	
	@JsonProperty("eventVersion")
	private String eventVersion;
	
	@JsonProperty("eventID")
	private String eventID;
	
	@JsonProperty("topic")
	private String topic;

}
