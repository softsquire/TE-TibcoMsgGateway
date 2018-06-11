package com.tesco.fulfillment.tibco.messaging.vo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.tesco.fulfillment.tibco.messaging.vo.ResponseOrderMessage;

@RunWith(MockitoJUnitRunner.class)
public class ResponseOrderMessageTest {
	
	@InjectMocks
    private ResponseOrderMessage responseOrderMsg;
	
	@Before
	public void init(){
		responseOrderMsg = new ResponseOrderMessage("PICKINGCOMPLETED event published successfully",HttpStatus.OK.toString());
	}

	@Test
	public void testGetMessage() {
		Assert.assertEquals("PICKINGCOMPLETED event published successfully", responseOrderMsg.getMessage());
	}
	
	@Test
	public void testGetStatusCode(){
		Assert.assertEquals("200", responseOrderMsg.getStatusCode());

	}
	
	@Test
	public void testToString() {
		Assert.assertEquals("ResponseOrderMessage(message=PICKINGCOMPLETED event published successfully, statusCode=200)", 
				responseOrderMsg.toString());
	}
	
	@After
	public void tearDown(){
		responseOrderMsg = null;
	}
	
	
}
