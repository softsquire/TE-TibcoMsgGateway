package com.tesco.fulfillment.tibco.messaging.vo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.tesco.fulfillment.tibco.messaging.vo.MessageHeader;

@RunWith(MockitoJUnitRunner.class)
public class MessageHeaderTest {
	
    @InjectMocks
	private MessageHeader messageHeader;
    
    @Before
    public void init(){
    	messageHeader = new MessageHeader("PICKING", "PICKINGCOMPLETED", "0.1", "1483a810-61eb-4952-a923-5da95287f680","topic01");
    }
    
	@Test
	public void testEventType() {
         Assert.assertEquals("PICKING", messageHeader.getEventType());
	}

	@Test
	public void testEventName() {
         Assert.assertEquals("PICKINGCOMPLETED", messageHeader.getEventName());
	}
	
	@Test
	public void testEventVersion() {
         Assert.assertEquals("0.1", messageHeader.getEventVersion());
	}
	
	@Test
	public void testEventId() {
         Assert.assertEquals("1483a810-61eb-4952-a923-5da95287f680", messageHeader.getEventID());
	}
	
	@Test
	public void testToString() {
         Assert.assertEquals("MessageHeader(eventType=PICKING, eventName=PICKINGCOMPLETED, eventVersion=0.1, eventID=1483a810-61eb-4952-a923-5da95287f680, topic=topic01)",
        		 messageHeader.toString());
	}	
}
