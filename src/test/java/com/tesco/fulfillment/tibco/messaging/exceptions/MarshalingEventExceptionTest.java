package com.tesco.fulfillment.tibco.messaging.exceptions;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.tesco.fulfillment.tibco.messaging.exceptions.MarshalingEventException;

@RunWith(MockitoJUnitRunner.class)
public class MarshalingEventExceptionTest {

	@InjectMocks
	private MarshalingEventException marshalingEventException;
	
	@Before
	public void init() {
		initMocks(this);
		marshalingEventException = new MarshalingEventException("StoreId_001","Marshaling Event Exception");
	}

	@Test
	public void shouldGetExceptionMessageWhenExceptionIsThrown() {
		assertEquals("Marshaling Event Exception", marshalingEventException.getMessage());
	}

	@Test
	public void shouldGetStoreIdWhenExceptionIsThrown() {
		assertEquals("StoreId_001", marshalingEventException.getStoreId());
	}

	@Test
	public void testToString() {
		Assert.assertEquals("MarshalingEventException(storeId=StoreId_001)", marshalingEventException.toString());
	}
}
