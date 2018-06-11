package com.tesco.fulfillment.tibco.messaging.api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tesco.fulfillment.tibco.messaging.api.MessagingApiController;
import com.tesco.fulfillment.tibco.messaging.exceptions.MarshalingEventException;
import com.tesco.fulfillment.tibco.messaging.exceptions.PickingEventException;
import com.tesco.fulfillment.tibco.messaging.service.MessagingApiService;
import com.tesco.fulfillment.tibco.messaging.testutil.TestUtil;
import com.tesco.fulfillment.tibco.messaging.vo.FulfillmentNotificationRequest;
import com.tesco.fulfillment.tibco.messaging.vo.ResponseOrderMessage;

@RunWith(MockitoJUnitRunner.class)
public class MessagingApiControllerTest {
	
    
    @Mock
    private MessagingApiService messagingService;
    
    @InjectMocks
    private MessagingApiController messagingApiController;
    
    @Test
    public void processOrderTestForPickingSuccess(){
    	
        Mockito.when(messagingService.publishExternalEvent(Matchers.any())).thenReturn(true);
        
		String messagePayload = TestUtil.getFile("pickingevent.json");

        FulfillmentNotificationRequest fulfillmentNotificationReq = buildRequestObject(messagePayload);
        
        ResponseEntity<ResponseOrderMessage> result = messagingApiController.publishLineItem(fulfillmentNotificationReq );
        assertEquals( HttpStatus.OK , result.getStatusCode());      
        assertEquals("PICKINGCOMPLETED event published successfully" , result.getBody().getMessage());      
    }
        
    @Test
    public void processOrderTestForMarshallingSuccess(){
    	
        Mockito.when(messagingService.publishExternalEvent(Matchers.any())).thenReturn(true);
        
		String messagePayload = TestUtil.getFile("marshallingevent.json");

        FulfillmentNotificationRequest fulfillmentNotificationReq = buildRequestObject(messagePayload);
        
        ResponseEntity<ResponseOrderMessage> result = messagingApiController.publishLineItem(fulfillmentNotificationReq );
        assertEquals( HttpStatus.OK , result.getStatusCode());      
        assertEquals("PACKAGEMARSHALLED event published successfully" , result.getBody().getMessage());      
    }

    
    @Test
    public void processOrderTestForPickTripCreatedEvent(){
    	
        Mockito.when(messagingService.publishExternalEvent(Matchers.any())).thenReturn(true);
        
		String messagePayload = TestUtil.getFile("picktripcreatedevent.json");

        FulfillmentNotificationRequest fulfillmentNotificationReq = buildRequestObject(messagePayload);
        
        ResponseEntity<ResponseOrderMessage> result = messagingApiController.publishLineItem(fulfillmentNotificationReq );
        assertEquals( HttpStatus.OK , result.getStatusCode());      
        assertEquals("PICKTRIPCREATED event published successfully" , result.getBody().getMessage());      
    }
    
    
    @Test(expected = PickingEventException.class)
    public void processOrderTestThrowsPickingEventException(){
    	
    	
        Mockito.when(messagingService.publishExternalEvent(Matchers.any())).thenThrow(new PickingEventException("2786","Picking event processing failed"));
        
		String messagePayload = TestUtil.getFile("pickingevent.json");

        FulfillmentNotificationRequest fulfillmentNotificationReq = buildRequestObject(messagePayload);
        
        ResponseEntity<ResponseOrderMessage> result = messagingApiController.publishLineItem(fulfillmentNotificationReq);
    }
    
    @Test(expected = MarshalingEventException.class)
    public void processOrderTestThrowsMarshallingEventException(){
    	
    	
        Mockito.when(messagingService.publishExternalEvent(Matchers.any())).thenThrow(new MarshalingEventException("2786","Marshalling event processing failed"));
        
		String messagePayload = TestUtil.getFile("marshallingevent.json");

        FulfillmentNotificationRequest fulfillmentNotificationReq = buildRequestObject(messagePayload);
        
        ResponseEntity<ResponseOrderMessage> result = messagingApiController.publishLineItem(fulfillmentNotificationReq);
    }

    private FulfillmentNotificationRequest buildRequestObject(String requestPayload){

		JSONObject jsonObj = new JSONObject(requestPayload);
    	ObjectMapper mapper = new ObjectMapper();
    	FulfillmentNotificationRequest request = null;
		try {
			request = mapper.readValue(jsonObj.toString(),FulfillmentNotificationRequest.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return request;	
    }
}
