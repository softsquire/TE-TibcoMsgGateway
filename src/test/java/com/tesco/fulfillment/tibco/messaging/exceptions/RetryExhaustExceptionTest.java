package com.tesco.fulfillment.tibco.messaging.exceptions;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RetryExhaustExceptionTest {

	@InjectMocks
    private RetryExhaustException retryExhaustException;
	
	@Before
	public void init() {
		initMocks(this);
		retryExhaustException = new RetryExhaustException("StoreId_001","RetryExhaust Exception");
	}
	
	@Test
	public void shouldGetExceptionMessageWhenExceptionIsThrown() {
		assertEquals("RetryExhaust Exception", retryExhaustException.getMessage());
	}

	@Test
	public void shouldGetStoreIdWhenExceptionIsThrown() {
		assertEquals("StoreId_001", retryExhaustException.getStoreId());
	}

	@Test
	public void testToString() {
		Assert.assertEquals("RetryExhaustException(storeId=StoreId_001)", retryExhaustException.toString());
	}	
}
