package com.tesco.fulfillment.tibco.messaging.utils;

import static com.tesco.fulfillment.tibco.messaging.utils.ApplicationConstants.MARSHALLING_EVENT_TYPE;
import static com.tesco.fulfillment.tibco.messaging.utils.ApplicationConstants.PICKING_EVENT_TYPE;

import java.util.Optional;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RetryServiceCommand<T> {	

	private long maxAttempts = 0;
	private long attempt = 0;
	private int retryDelay = 0;	
	private String eventType = null;
	
        
    public RetryServiceCommand() {
    	
    }
    
    public RetryServiceCommand(String eventType,int retryDelay,int maxAttempts) {
    	this.eventType = eventType;
    	this.retryDelay = retryDelay;
    	this.maxAttempts = maxAttempts;
    }
     
    
   public Optional<T> retry(Supplier<T> supplierMethod) {
    	boolean done = false;
        T serviceResponse = null;        
        do {
         try{	
			++attempt;
			logAlertMesageForTibcoServiceRetry(eventType);
			log.info("retry attempt :{}",  attempt);
			serviceResponse = supplierMethod.get();
			log.info("Response from the external service:{}", serviceResponse);
			done = (Boolean) serviceResponse;
            if (done) {
    			log.info("Response from the external service!{}", serviceResponse);
            } else if (hasRemainingAttempts()) {
            	log.info("waiting for ms:{}",retryDelay);
                Thread.sleep(retryDelay);                   
                
            } else {
            	log.info("all retry attempts done---> giving up:{}",serviceResponse);
                logAlertMesageForTibcoServiceRetry(eventType);
                logAlertMesageForGenericExceptions(eventType);
            }
         } catch (Exception exception) {
        	 log.info("{}, interrupted!",exception);
              // Restore the interrupted status             
             serviceResponse  = (T)new Boolean(false);
             Thread.currentThread().interrupt();
              break;
          }
        } while (!done && hasRemainingAttempts());
        return Optional.ofNullable(serviceResponse);
    }

    private boolean hasRemainingAttempts() {
        return attempt < maxAttempts;
    }
    
    private void logAlertMesageForTibcoServiceRetry(String eventType) {
		if (eventType.equals(PICKING_EVENT_TYPE)) {
			log.error(LoggingConstants.RETRY_ERROR_FOR_PUBLISH_PICKING_COMPLETED_CODE+" {}",LoggingConstants.RETRY_ERROR_FOR_PUBLISH_PICKING_COMPLETED);
			
		} else if(eventType.equals(MARSHALLING_EVENT_TYPE)){
			log.error(LoggingConstants.RETRY_ERROR_FOR_PUBLISH_PARCEL_MARSHALLED_CODE+" {}",LoggingConstants.RETRY_ERROR_FOR_PUBLISH_PARCEL_MARSHALLED);
		}		
		
	}
	private void logAlertMesageForGenericExceptions(String eventType) {
		if (eventType.equals(PICKING_EVENT_TYPE)) {
			log.error(LoggingConstants.ERROR_PUBLISH_FAILED_PICKING_COMPLETED_CODE+ " {}",LoggingConstants.ERROR_PUBLISH_FAILED_PICKING_COMPLETED);
		}else if(eventType.equals(MARSHALLING_EVENT_TYPE)){
			log.error(LoggingConstants.ERROR_PUBLISH_FAILED_PARCEL_MARSHALLED_CODE+ " {}",LoggingConstants.ERROR_PUBLISH_FAILED_PARCEL_MARSHALLED);
		} 
	}

}
