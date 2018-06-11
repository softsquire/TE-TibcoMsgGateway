package com.tesco.fulfillment.tibco.messaging.service;


import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tesco.fulfillment.tibco.messaging.events.ExternalEventPublisher;
import com.tesco.fulfillment.tibco.messaging.events.GenericExternalEvent;
import com.tesco.fulfillment.tibco.messaging.exceptions.MarshalingEventException;
import com.tesco.fulfillment.tibco.messaging.exceptions.PickingEventException;
import com.tesco.fulfillment.tibco.messaging.exceptions.RetryExhaustException;
import com.tesco.fulfillment.tibco.messaging.utils.ApplicationConstants;

@Slf4j
@Service
public class MessagingApiService {
	
	@Autowired
	private ExternalEventPublisher externalEventPublisher;
	
	public boolean publishExternalEvent(GenericExternalEvent<String> genericEvent) {
		boolean status=false;
		String eventType = genericEvent.getHeader().getEventType();
		try {
			status = externalEventPublisher.publishMessage(genericEvent);
            if(!status)
            throw new RetryExhaustException(MDC.get(ApplicationConstants.STOREID), "Messaging layer (TIBCO) connectivity broken");
		}catch(RetryExhaustException e){
			throw e;
		}
		catch (Exception ex) {
			log.error(eventType+" event processing failed due to Internal server Error {}", ex);
			if(eventType.equalsIgnoreCase(ApplicationConstants.PICKING_EVENT_TYPE)){
				throw new PickingEventException(MDC.get(ApplicationConstants.STOREID),"Picking event processing failed due to Internal server Error, exception: "+ex);
			} else if(eventType.equalsIgnoreCase(ApplicationConstants.MARSHALLING_EVENT_TYPE)){
				throw new MarshalingEventException(MDC.get(ApplicationConstants.STOREID),"Marshalling event processing failed due to Internal server Error, exception:  "+ex);
			}
		} 
		return status;
	}
}
