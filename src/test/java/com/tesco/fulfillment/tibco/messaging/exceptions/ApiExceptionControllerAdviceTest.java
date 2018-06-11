package com.tesco.fulfillment.tibco.messaging.exceptions;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tesco.fulfillment.tibco.messaging.utils.ApplicationConstants;
import com.tesco.fulfillment.tibco.messaging.vo.ResponseOrderMessage;


@RunWith(MockitoJUnitRunner.class)
public class ApiExceptionControllerAdviceTest {

	@InjectMocks
	private ApiExceptionControllerAdvice apiExceptionControllerAdvice;
	
	@After
	public void init(){
		MDC.clear();
	}
	
	@Test
	public void testPickingEventExceptionHandler() {
		PickingEventException exception = new PickingEventException("StoreId_001","PickingEventException!!");		
		HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
		ResponseEntity<Object> response = apiExceptionControllerAdvice.pickingEventExceptionHandler(exception);
		assertEquals(statusCode, response.getStatusCode());
	}
	
		
	@Test
	public void testMarshalingEventExceptionHandler() {
		MarshalingEventException exception = new MarshalingEventException("StoreId_001","Invalid Marshalling Event Data");
		HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
		ResponseEntity<Object> response = apiExceptionControllerAdvice.marshalingEventExceptionHandler(exception);
		assertEquals(statusCode, response.getStatusCode());
	}
	
	@Test
	public void testRetryExhaustExceptionHandlerForPickingEvent() {
		MDC.put(ApplicationConstants.EVENTTYPE,"PICKING");
		RetryExhaustException exception = new RetryExhaustException("StoreId_001","Messaging layer (TIBCO) connectivity broken");		
		ResponseEntity<ResponseOrderMessage> response = apiExceptionControllerAdvice.retryExhaustExceptionHandler(exception);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Picking event processing failed due to Internal server Error", response.getBody().getMessage());
		assertEquals("6002", response.getBody().getStatusCode());


	}
	
	@Test
	public void testRetryExhaustExceptionHandlerForMarshallingEvent() {
		MDC.put(ApplicationConstants.EVENTTYPE,"MARSHALLING");
		RetryExhaustException exception = new RetryExhaustException("StoreId_001","Messaging layer (TIBCO) connectivity broken");		
		ResponseEntity<ResponseOrderMessage> response = apiExceptionControllerAdvice.retryExhaustExceptionHandler(exception);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Marshalling event processing failed due to Internal server Error", response.getBody().getMessage());
		assertEquals("7004", response.getBody().getStatusCode());


	}

	
	
	
}
