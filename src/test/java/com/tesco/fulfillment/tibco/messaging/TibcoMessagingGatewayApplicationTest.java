package com.tesco.fulfillment.tibco.messaging;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.tesco.fulfillment.tibco.messaging.TibcoMessagingGatewayApplication;

@RunWith(MockitoJUnitRunner.class)
public class TibcoMessagingGatewayApplicationTest {
	
	@InjectMocks
    private TibcoMessagingGatewayApplication application;
		
	@Before
	public void init(){
		initMocks(this);
	}
	
	@Test
	public void testMain(){
		
	}
}
