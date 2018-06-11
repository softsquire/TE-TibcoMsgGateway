package com.tesco.fulfillment.tibco.messaging.configuration;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.tesco.fulfillment.tibco.messaging.configuration.EventConfiguration;


@RunWith(MockitoJUnitRunner.class)
public class EventConfigurationTest {

	@InjectMocks
	private EventConfiguration eventConfiguration;
	
	@Before
	public void init() {
		initMocks(this);
		eventConfiguration = new EventConfiguration();
	}
	
	@Test
	public void testGetServerUrl() {
		eventConfiguration.setServerUrl("sampleUrl");
       assertEquals("sampleUrl",eventConfiguration.getServerUrl());
	}
	
	@Test
	public void testGetUserName() {
		eventConfiguration.setUserName("admin");
       assertEquals("admin",eventConfiguration.getUserName());
	}

	@Test
	public void testGetPassword() {
		eventConfiguration.setPassword("abc");
       assertEquals("abc",eventConfiguration.getPassword());
	}

	@Test
	public void testGetMaxRetryCount() {
		eventConfiguration.setMaxRetryCount(3);
       assertEquals(3,eventConfiguration.getMaxRetryCount());
	}

	@Test
	public void testGetRetryDelay() {
		eventConfiguration.setRetryDelay(100);
       assertEquals(100,eventConfiguration.getRetryDelay());
	}
	
	@Test
	public void testGetTimeout() {
		eventConfiguration.setTimeOut((500));
       assertEquals(500,eventConfiguration.getTimeOut());
	}
	
	@Test
	public void testGetTopicName() {
		eventConfiguration.setTopicName("topic001");
       assertEquals("topic001",eventConfiguration.getTopicName());
	}
}
