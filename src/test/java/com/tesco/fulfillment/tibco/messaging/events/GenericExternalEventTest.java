package com.tesco.fulfillment.tibco.messaging.events;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tesco.fulfillment.tibco.messaging.events.GenericExternalEvent;
import com.tesco.fulfillment.tibco.messaging.testutil.TestUtil;
import com.tesco.fulfillment.tibco.messaging.vo.MessageHeader;
import com.tesco.fulfillment.tibco.messaging.vo.MessagePayload;

@RunWith(MockitoJUnitRunner.class)
public class GenericExternalEventTest {
	
	@InjectMocks
	private  GenericExternalEvent<String> genericExternalEvent;
	
	@Before
	public void init() {
		String messagePayload = TestUtil.getFile("pickingevent.json");
		genericExternalEvent = buildGenericExternalEvent(messagePayload);
	}
	
	@Test
	public void testGetPayload() {
		
		ObjectMapper mapper = new ObjectMapper();
		MessagePayload messagePayload = null;
			JsonNode jsonNode;
			try {
				jsonNode = mapper
						.readTree(genericExternalEvent.getPayload());
				messagePayload = mapper.readValue(jsonNode.toString(),
						MessagePayload.class);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		assertEquals("2786", messagePayload.getFulfillmentNodeID());
		assertEquals("OFS:GMO:XYZ99-4", messagePayload.getFulfilmentOrderID());
		assertEquals("9TES27860000002", messagePayload.getPickedPackageID());
	}
	
	@Test
	public void testGetHeader() {
		assertEquals("PICKING", genericExternalEvent.getHeader().getEventType());
		assertEquals("PICKINGCOMPLETED", genericExternalEvent.getHeader().getEventName());
		assertEquals("0.1", genericExternalEvent.getHeader().getEventVersion());
	}
	@Test
	public void testToString() {
		assertEquals("GenericExternalEvent(header=MessageHeader(eventType=PICKING, eventName=PICKINGCOMPLETED, eventVersion=0.1, eventID=1483a810-61eb-4952-a923-5da95287f680, topic=topic01), payload={\"pickedPackageID\":\"9TES27860000002\",\"fulfilmentOrderID\":\"OFS:GMO:XYZ99-4\",\"fulfillmentNodeID\":\"2786\",\"items\":[{\"plannedQty\":\"1\",\"orderlineID\":\"1\",\"pickedProductTpnb\":\"073015194\",\"reasonCode\":\"NA\",\"orderedProductTpnb\":\"073015194\",\"timestamp\":\"2016-07-14T09:10:00.6212Z\",\"pickedQty\":\"1\",\"status\":\"Success\"}]})",genericExternalEvent.toString());
	}
	
	
	private GenericExternalEvent<String> buildGenericExternalEvent(String payload){
		
		JSONObject jsonObj = new JSONObject(payload);
		JSONObject headerObj =jsonObj.getJSONObject("messageHeader");
		JSONObject body = jsonObj.getJSONObject("messageBody");

		ObjectMapper mapper = new ObjectMapper();
		MessageHeader header = null;
		try {
			header = mapper.readValue(headerObj.toString(),MessageHeader.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		GenericExternalEvent<String> externalEventData = new GenericExternalEvent<>();
		externalEventData.setHeader(header);
		externalEventData.setPayload(body.toString());
		return externalEventData;
	}	
	

}
