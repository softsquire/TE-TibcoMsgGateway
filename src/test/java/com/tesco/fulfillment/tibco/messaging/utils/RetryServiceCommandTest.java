package com.tesco.fulfillment.tibco.messaging.utils;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import javax.jms.JMSException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.tesco.fulfillment.tibco.messaging.events.GenericExternalEvent;
import com.tesco.fulfillment.tibco.messaging.exceptions.TibcoConnectionException;
import com.tesco.fulfillment.tibco.messaging.repositories.IMessageRepository;
import com.tesco.fulfillment.tibco.messaging.utils.RetryServiceCommand;
import com.tesco.fulfillment.tibco.messaging.vo.MessageHeader;

@RunWith(MockitoJUnitRunner.class)
public class RetryServiceCommandTest {
	
	
	@InjectMocks
	private RetryServiceCommand<Boolean> retryServiceCommand;
	
	@Mock
	private IMessageRepository gmTibcoMessegeServiceDao;
	
		
	@Before
	public void setup() {		
		retryServiceCommand = new RetryServiceCommand<>("PICKING",100,4);
	}	

	@Test
	public void shouldRetryPublishThreeTimesWHenUnableToConnectTibcoService() throws TibcoConnectionException, JMSException{
		
		GenericExternalEvent<String> genericExternalEvent = new GenericExternalEvent<>();
		genericExternalEvent.setPayload("Message");
		genericExternalEvent.setHeader(new MessageHeader());
		
		Mockito.when(gmTibcoMessegeServiceDao.publish(genericExternalEvent.getPayload(), 
				genericExternalEvent.getHeader())).thenReturn(false);
		
		Optional<Boolean> status = retryServiceCommand.retry(()->{
			boolean result = false;
			try {
				result= gmTibcoMessegeServiceDao.publish(genericExternalEvent.getPayload(),
						genericExternalEvent.getHeader());
			} catch (TibcoConnectionException | JMSException e) {
				
			}
			return result;
		});		
	
		assertEquals(false, status.get());
	}
	
	@Test
	public void shouldNotRetryPublishThreeTimesWhenExceptionOccurs() throws TibcoConnectionException, JMSException{
		
		GenericExternalEvent<String> genericExternalEvent = new GenericExternalEvent<>();
		genericExternalEvent.setPayload("Message");
		genericExternalEvent.setHeader(new MessageHeader());
		
		Mockito.when(gmTibcoMessegeServiceDao.publish(genericExternalEvent.getPayload(), genericExternalEvent.getHeader())).
				thenThrow(new TibcoConnectionException("TibcoConnectionException!!","ERR01"));
		
		Optional<Boolean> status = retryServiceCommand.retry(()->{
			boolean result = false;
			try {
				result= gmTibcoMessegeServiceDao.publish(genericExternalEvent.getPayload(),
						genericExternalEvent.getHeader());
			} catch (TibcoConnectionException | JMSException e) {
				
			}
			return result;
		});		
	
		assertEquals(false, status.get());
	}
}
