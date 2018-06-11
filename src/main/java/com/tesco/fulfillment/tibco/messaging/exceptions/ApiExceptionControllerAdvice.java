package com.tesco.fulfillment.tibco.messaging.exceptions;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tesco.fulfillment.tibco.messaging.utils.ApplicationConstants;
import com.tesco.fulfillment.tibco.messaging.utils.LoggingConstants;
import com.tesco.fulfillment.tibco.messaging.vo.ResponseOrderMessage;

@ControllerAdvice
@Slf4j
public class ApiExceptionControllerAdvice {

	@ExceptionHandler(PickingEventException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> pickingEventExceptionHandler(
			PickingEventException exceptionMessage) {		
        ResponseOrderMessage response = new ResponseOrderMessage("Picking event processing failed due to Internal server Error",
        	LoggingConstants.ERROR_PUBLISH_FAILED_PICKING_COMPLETED_CODE);

		log.error(LoggingConstants.ERROR_PUBLISH_FAILED_PICKING_COMPLETED_CODE+" {}", LoggingConstants.ERROR_PUBLISH_FAILED_PICKING_COMPLETED
				+"for storeId:: "+exceptionMessage.getStoreId());
		log.error("exception message::{}",exceptionMessage.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	@ExceptionHandler(MarshalingEventException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> marshalingEventExceptionHandler(
			MarshalingEventException exceptionMessage) {
        ResponseOrderMessage response = new ResponseOrderMessage("Marshalling event processing failed due to Internal server Error",
        		LoggingConstants.ERROR_PUBLISH_FAILED_PARCEL_MARSHALLED_CODE);

		log.error(LoggingConstants.ERROR_PUBLISH_FAILED_PARCEL_MARSHALLED_CODE+ " {}", LoggingConstants.ERROR_PUBLISH_FAILED_PARCEL_MARSHALLED
				+"for StoreId::"+exceptionMessage.getStoreId());
		log.error("exception message::{}", exceptionMessage.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	@ExceptionHandler(RetryExhaustException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ResponseOrderMessage> retryExhaustExceptionHandler(
			RetryExhaustException exceptionMessage) {
		ResponseOrderMessage response = null;
		String eventType = MDC.get(ApplicationConstants.EVENTTYPE);
		if(eventType.equalsIgnoreCase(ApplicationConstants.PICKING_EVENT_TYPE)){
           response = new ResponseOrderMessage("Picking event processing failed due to Internal server Error",
        		   LoggingConstants.ERROR_PUBLISH_FAILED_PICKING_COMPLETED_CODE);
/*   		log.error(LoggingConstants.ERROR_PUBLISH_FAILED_PICKING_COMPLETED_CODE+" {}" , LoggingConstants.ERROR_PUBLISH_FAILED_PICKING_COMPLETED
   				+" for storeId::"+exceptionMessage.getStoreId());
*/		} else if(eventType.equalsIgnoreCase(ApplicationConstants.MARSHALLING_EVENT_TYPE)){
			response = new ResponseOrderMessage("Marshalling event processing failed due to Internal server Error",
	        		LoggingConstants.ERROR_PUBLISH_FAILED_PARCEL_MARSHALLED_CODE);
/*		log.error(LoggingConstants.ERROR_PUBLISH_FAILED_PARCEL_MARSHALLED_CODE+ " {}", LoggingConstants.ERROR_PUBLISH_FAILED_PARCEL_MARSHALLED
				+" for storeId::"+exceptionMessage.getStoreId());
*/		}
		log.error("exception message::{}", exceptionMessage.getMessage());
		return new ResponseEntity<ResponseOrderMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
}
