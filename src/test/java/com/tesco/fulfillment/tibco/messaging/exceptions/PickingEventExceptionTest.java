package com.tesco.fulfillment.tibco.messaging.exceptions;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.tesco.fulfillment.tibco.messaging.exceptions.PickingEventException;

@RunWith(MockitoJUnitRunner.class)
public class PickingEventExceptionTest {
	
	@InjectMocks
	private PickingEventException pickingEventException;
	
	@Before
	public void init() {
		initMocks(this);
		pickingEventException = new PickingEventException("StoreId_001","Picking Event Exception");
	}

	@Test
	public void shouldGetExceptionMessageWhenExceptionIsThrown() {
		assertEquals("Picking Event Exception", pickingEventException.getMessage());
	}

	@Test
	public void shouldGetStoreIdWhenExceptionIsThrown() {
		assertEquals("StoreId_001", pickingEventException.getStoreId());
	}

	@Test
	public void testToString() {
		Assert.assertEquals("PickingEventException(storeId=StoreId_001)", pickingEventException.toString());
	}
}
