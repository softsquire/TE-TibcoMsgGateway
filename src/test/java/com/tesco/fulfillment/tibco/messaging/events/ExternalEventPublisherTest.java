package com.tesco.fulfillment.tibco.messaging.events;

import java.io.IOException;

import javax.jms.JMSException;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.MDC;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tesco.fulfillment.tibco.messaging.configuration.EventConfiguration;
import com.tesco.fulfillment.tibco.messaging.events.ExternalEventPublisher;
import com.tesco.fulfillment.tibco.messaging.events.GenericExternalEvent;
import com.tesco.fulfillment.tibco.messaging.exceptions.TibcoConnectionException;
import com.tesco.fulfillment.tibco.messaging.repositories.IMessageRepository;
import com.tesco.fulfillment.tibco.messaging.testutil.TestUtil;
import com.tesco.fulfillment.tibco.messaging.vo.MessageHeader;

@RunWith(MockitoJUnitRunner.class)
public class ExternalEventPublisherTest {
	
	@InjectMocks
    private ExternalEventPublisher externalEventPublisher;
	
	@Mock
    private IMessageRepository iMessageRepository;
	
	@Mock
	private EventConfiguration eventConfiguration;
	
	@Before
	public void setup(){		
		Mockito.when(eventConfiguration.getMaxRetryCount()).thenReturn(4);
		Mockito.when(eventConfiguration.getRetryDelay()).thenReturn(10);
	}	
	@After
	public void tearDown(){
		MDC.clear();
	}
	

	@Test
	public void publishMessageSuccessForPickingTest() throws TibcoConnectionException, JMSException, InterruptedException {
		
		String messagePayload = TestUtil.getFile("pickingevent.json");
		GenericExternalEvent<String> genericEvent = buildGenericExternalEvent(messagePayload);
		Mockito.when(iMessageRepository.publish(Matchers.anyString(), Matchers.any(MessageHeader.class))).thenReturn(true);
		Assert.assertEquals(true, externalEventPublisher.publishMessage(genericEvent));
	}
	
	@Test
	public void publishMessageSucessForMarshallingTest() throws TibcoConnectionException, JMSException, InterruptedException {
		
		String messagePayload = TestUtil.getFile("marshallingevent.json");
		GenericExternalEvent<String> genericEvent = buildGenericExternalEvent(messagePayload);
		Mockito.when(iMessageRepository.publish(Matchers.anyString(), Matchers.any(MessageHeader.class))).thenReturn(true);
		Assert.assertEquals(true, externalEventPublisher.publishMessage(genericEvent));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void publishMessageAndRetryThreeTimesWhenJMSExceptionOccursTest() throws TibcoConnectionException, JMSException, InterruptedException {
		Mockito.when(iMessageRepository.publish(Matchers.anyString(), Matchers.any(MessageHeader.class)))
				.thenThrow(JMSException.class);
		String messagePayload = TestUtil.getFile("pickingevent.json");
		GenericExternalEvent<String> genericEvent = buildGenericExternalEvent(messagePayload);
		Assert.assertEquals(false, externalEventPublisher.publishMessage(genericEvent));
		Mockito.verify(iMessageRepository, Mockito.times(4)).publish(Matchers.anyString(), Matchers.any(MessageHeader.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void publishMessageAndRetryThreeTimesWhenTibcoConnectionExceptionOccursTest() throws TibcoConnectionException, 
	JMSException, InterruptedException {
		Mockito.when(iMessageRepository.publish(Matchers.anyString(),  Matchers.any(MessageHeader.class)))
		.thenThrow(TibcoConnectionException.class);
		String messagePayload = TestUtil.getFile("pickingevent.json");
		GenericExternalEvent<String> genericEvent = buildGenericExternalEvent(messagePayload);
		Assert.assertEquals(false, externalEventPublisher.publishMessage(genericEvent));
		Mockito.verify(iMessageRepository, Mockito.times(4)).publish(Matchers.anyString(), Matchers.any(MessageHeader.class));
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
