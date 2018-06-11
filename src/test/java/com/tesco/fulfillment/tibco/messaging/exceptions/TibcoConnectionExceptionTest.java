package com.tesco.fulfillment.tibco.messaging.exceptions;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.tesco.fulfillment.tibco.messaging.exceptions.TibcoConnectionException;

@RunWith(MockitoJUnitRunner.class)
public class TibcoConnectionExceptionTest {
	
	@InjectMocks
	private TibcoConnectionException tibcoConnectionException;

	@Before
	public void init() {
		initMocks(this);
		tibcoConnectionException = new TibcoConnectionException("Unable to connect to Tibco","CONNECT EXCEPTION");
	}
	
	@Test
	public void testGetErrorCode() {
		assertEquals("CONNECT EXCEPTION",
				tibcoConnectionException.getErrorCode());
	}
	
	@Test
	public void testGetErrorMessage() {
		assertEquals("Unable to connect to Tibco",
				tibcoConnectionException.getMessage());
	}
	
	@Test
	public void testToString() {
		Assert.assertEquals("TibcoConnectionException(errorCode=CONNECT EXCEPTION)", tibcoConnectionException.toString());
	}
	
}
