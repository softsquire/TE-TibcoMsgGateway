package com.tesco.fulfillment.tibco.messaging.events;

import com.tesco.fulfillment.tibco.messaging.vo.MessageHeader;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GenericExternalEvent<T> {
	
	private MessageHeader header;
	private T payload;

}
