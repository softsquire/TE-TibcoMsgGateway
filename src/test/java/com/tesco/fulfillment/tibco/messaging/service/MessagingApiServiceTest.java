package com.tesco.fulfillment.tibco.messaging.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.jms.JMSException;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tesco.fulfillment.tibco.messaging.events.ExternalEventPublisher;
import com.tesco.fulfillment.tibco.messaging.events.GenericExternalEvent;
import com.tesco.fulfillment.tibco.messaging.exceptions.MarshalingEventException;
import com.tesco.fulfillment.tibco.messaging.exceptions.PickingEventException;
import com.tesco.fulfillment.tibco.messaging.exceptions.RetryExhaustException;
import com.tesco.fulfillment.tibco.messaging.exceptions.TibcoConnectionException;
import com.tesco.fulfillment.tibco.messaging.service.MessagingApiService;
import com.tesco.fulfillment.tibco.messaging.testutil.TestUtil;
import com.tesco.fulfillment.tibco.messaging.vo.MessageHeader;

@RunWith(MockitoJUnitRunner.class)
public class MessagingApiServiceTest {
	
	@InjectMocks
    private MessagingApiService messageApiService;
	
	@Mock
	private ExternalEventPublisher externalEventPublisher;
	
	@Test
	public void testPublishExternalEventForPickingReturnsSuccess () throws TibcoConnectionException, JMSException, InterruptedException{
		
		String messagePayload = TestUtil.getFile("pickingevent.json");
		GenericExternalEvent<String> genericEvent = buildGenericExternalEvent(messagePayload);
		Mockito.when(externalEventPublisher.publishMessage(Mockito.any())).thenReturn(true);
		boolean flag = messageApiService.publishExternalEvent(genericEvent);
		assertEquals(true, flag);
	}
	
	
	@Test
	public void testPublishExternalEventForMarshallingReturnsSuccess () throws TibcoConnectionException, JMSException, InterruptedException{
		
		String messagePayload = TestUtil.getFile("marshallingevent.json");
		GenericExternalEvent<String> genericEvent = buildGenericExternalEvent(messagePayload);
		Mockito.when(externalEventPublisher.publishMessage(Mockito.any())).thenReturn(true);
		boolean flag = messageApiService.publishExternalEvent(genericEvent);
		assertEquals(true, flag);
	}

	
	
	@Test(expected= PickingEventException.class)
	public void testPublishExternalEventthrowsPickingEventException () throws TibcoConnectionException, JMSException, InterruptedException{
		
		String messagePayload = TestUtil.getFile("pickingevent.json");
		GenericExternalEvent<String> genericEvent = buildGenericExternalEvent(messagePayload);
		Mockito.when(externalEventPublisher.publishMessage(Mockito.any())).thenThrow(new TibcoConnectionException("TibcoConnectionException",
				"errcode"));
		messageApiService.publishExternalEvent(genericEvent);
	}

	
	@Test(expected= MarshalingEventException.class)
	public void testPublishExternalEventthrowsMarshallingEventException () throws TibcoConnectionException, JMSException, InterruptedException{
		
		String messagePayload = TestUtil.getFile("marshallingevent.json");
		GenericExternalEvent<String> genericEvent = buildGenericExternalEvent(messagePayload);
		Mockito.when(externalEventPublisher.publishMessage(Mockito.any())).thenThrow(new TibcoConnectionException("TibcoConnectionException",
				"errcode"));
		messageApiService.publishExternalEvent(genericEvent);
	}

	
	@Test(expected= MarshalingEventException.class)
	public void testPublishExternalEventMarshallingForJMSException () throws TibcoConnectionException, JMSException, InterruptedException{
		
		String messagePayload = TestUtil.getFile("marshallingevent.json");
		GenericExternalEvent<String> genericEvent = buildGenericExternalEvent(messagePayload);
		Mockito.when(externalEventPublisher.publishMessage(Mockito.any())).thenThrow(new JMSException("JMSException"));
		messageApiService.publishExternalEvent(genericEvent);
	}

	@Test(expected= PickingEventException.class)
	public void testPublishExternalEventPickingForJMSException () throws TibcoConnectionException, JMSException, InterruptedException{
		
		String messagePayload = TestUtil.getFile("pickingevent.json");
		GenericExternalEvent<String> genericEvent = buildGenericExternalEvent(messagePayload);
		Mockito.when(externalEventPublisher.publishMessage(Mockito.any())).thenThrow(new JMSException("JMSException"));
		messageApiService.publishExternalEvent(genericEvent);
	}
	
	@Test(expected= RetryExhaustException.class)
	public void testPublishExternalEventPickingForRetryExhaustException () throws TibcoConnectionException, JMSException, InterruptedException{
		
		String messagePayload = TestUtil.getFile("pickingevent.json");
		GenericExternalEvent<String> genericEvent = buildGenericExternalEvent(messagePayload);
		Mockito.when(externalEventPublisher.publishMessage(Mockito.any())).thenReturn(false);
		messageApiService.publishExternalEvent(genericEvent);
	}
	
	
	@Test(expected= RetryExhaustException.class)
	public void testPublishExternalEventMarshallingForRetryExhaustException () throws TibcoConnectionException, JMSException, InterruptedException{
		
		String messagePayload = TestUtil.getFile("marshallingevent.json");
		GenericExternalEvent<String> genericEvent = buildGenericExternalEvent(messagePayload);
		Mockito.when(externalEventPublisher.publishMessage(Mockito.any())).thenReturn(false);
		messageApiService.publishExternalEvent(genericEvent);
	}
	
	private GenericExternalEvent<String> buildGenericExternalEvent(String payload){
		
		JSONObject jsonObj = new JSONObject(payload);
		JSONObject headerObj =jsonObj.getJSONObject("messageHeader");
		JSONObject body = jsonObj.getJSONObject("messageBody");

		ObjectMapper mapper = new ObjectMapper();
		MessageHeader header = null;
		try {
			header = mapper.readValue(headerObj.toString(),MessageHeader.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		GenericExternalEvent<String> externalEventData = new GenericExternalEvent<>();
		externalEventData.setHeader(header);
		externalEventData.setPayload(body.toString());
		return externalEventData;
	}

}
