package com.tesco.fulfillment.tibco.messaging.exceptions;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.tesco.fulfillment.tibco.messaging.exceptions.MessagePayloadValidationException;


@RunWith(MockitoJUnitRunner.class)
public class MessagePayloadValidationExceptionTest {
	
	@InjectMocks
	private MessagePayloadValidationException messagePayloadValidationException;
	
	@Before
	public void init() {
		initMocks(this);
		messagePayloadValidationException = new MessagePayloadValidationException("Message Validation Failed!");
	}

	@Test
	public void testGetMessage() {
		assertEquals("Message Validation Failed!",
				messagePayloadValidationException.getMessage());
	}

	@Test
	public void testToString() {
		Assert.assertEquals("MessagePayloadValidationException()", messagePayloadValidationException.toString());
	}

}
