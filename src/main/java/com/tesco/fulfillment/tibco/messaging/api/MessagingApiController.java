package com.tesco.fulfillment.tibco.messaging.api;


import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tesco.fulfillment.tibco.messaging.events.GenericExternalEvent;
import com.tesco.fulfillment.tibco.messaging.service.MessagingApiService;
import com.tesco.fulfillment.tibco.messaging.utils.ApplicationConstants;
import com.tesco.fulfillment.tibco.messaging.vo.FulfillmentNotificationRequest;
import com.tesco.fulfillment.tibco.messaging.vo.MessageHeader;
import com.tesco.fulfillment.tibco.messaging.vo.ResponseOrderMessage;

@Slf4j
@RestController
@RequestMapping(value = "/messagingapi")
public class MessagingApiController {
		
	@Autowired
	private MessagingApiService messagingService;
	
	@RequestMapping(value = "/publish", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<ResponseOrderMessage> publishLineItem(@RequestBody FulfillmentNotificationRequest fulfillmentNotificationRequest ) {
		long startTime = System.nanoTime();		
		String eventName = fulfillmentNotificationRequest.getEventMessageHeader().getEventName();
		String topic = fulfillmentNotificationRequest.getEventMessageHeader().getTopic();
		log.info("Payload recieved from FNS:: {} with EventName:{} for Topic::{}", fulfillmentNotificationRequest.toString(),eventName,
				topic);
		setMdcVariables(fulfillmentNotificationRequest);
		//boolean validationFlag = messageValidator.validate(fulfillmentNotificationRequest);
		//if(validationFlag){
		GenericExternalEvent<String> genericEvent = buildGenericEventData(fulfillmentNotificationRequest.getEventMessageHeader(),
				fulfillmentNotificationRequest.getMessagePayload());
		//}
		messagingService.publishExternalEvent(genericEvent);
		//time elapsed
		long elapsedTime = System.nanoTime() - startTime;
        log.info("publishLineItem-->Time elapsed in miliseconds :{}", elapsedTime / 1000000);
		MDC.clear();
		return new ResponseEntity<ResponseOrderMessage>(new ResponseOrderMessage(eventName+" event published successfully",HttpStatus.OK.toString()),HttpStatus.OK);
	}

	private GenericExternalEvent<String> buildGenericEventData(MessageHeader header, Object messagePayload ){
		
		JSONObject payload = new JSONObject(messagePayload);
		GenericExternalEvent<String> externalEventData = new GenericExternalEvent<>();
		externalEventData.setHeader(header);
		externalEventData.setPayload(payload.toString());
		return externalEventData;
	}
		
	private static void setMdcVariables(FulfillmentNotificationRequest fulfillmentNotificationObject){	
		String storeId = fulfillmentNotificationObject.getMessagePayload().getFulfillmentNodeID()==null?fulfillmentNotificationObject.getMessagePayload().getStoreId():fulfillmentNotificationObject.getMessagePayload().getFulfillmentNodeID();
		MDC.put(ApplicationConstants.STOREID, storeId);
		MDC.put(ApplicationConstants.EVENTID, fulfillmentNotificationObject.getEventMessageHeader().getEventID() != null ? fulfillmentNotificationObject.getEventMessageHeader().getEventID() : null);
		MDC.put(ApplicationConstants.EVENTTYPE, fulfillmentNotificationObject.getEventMessageHeader().getEventType() !=null ? fulfillmentNotificationObject.getEventMessageHeader().getEventType() : null);
	}
}
