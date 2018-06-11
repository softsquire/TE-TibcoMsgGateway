package com.tesco.fulfillment.tibco.messaging.configuration;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Setter
@Getter
public class EventConfiguration {	
	
	@Value("${tibco.ems.serverUrl}")
	private String serverUrl;	

	@Value("${tibco.ems.userName}")
	private String userName;	

	@Value("${tibco.ems.password}")
	private String password;
	
	@Value("${tibco.ems.topic}")
	private String topicName;	

	@Value("${tibco.ems.timeOut}")
	private int timeOut;
	
	@Value("${tibco.ems.retryCount}")
	private int maxRetryCount;
	
	@Value("${tibco.ems.retryDelay}")
	private int retryDelay;
	
}
