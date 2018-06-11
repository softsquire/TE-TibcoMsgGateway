package com.tesco.fulfillment.tibco.messaging.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown=true)
public class MessagePayload {
	
	//@NotNull(message = "pickedPackageID cannot be null")
	@JsonProperty("pickedPackageID")
	private String pickedPackageID;
	
	//@NotNull(message = "fulfilmentOrderID cannot be null")
	@JsonProperty("fulfilmentOrderID")
	private String fulfilmentOrderID;
	
	//@NotNull(message = "fulfillmentNodeID cannot be null")
	@JsonProperty("fulfillmentNodeID")
	private String fulfillmentNodeID;

	//@NotNull(message = "location cannot be null")
	@JsonProperty("location")
	public String location;
	
	@JsonProperty("timestamp")
	public String timestamp;
	
	//@Size(min=1, message="lineItems cannot be empty")
	//@NotNull(message = "lineItems cannot be null")
	@JsonProperty("items")
	private List<LineItem> items;
	
	@JsonProperty("picktripId")
	private String picktripId;
	
	@JsonProperty("deliveryType")
	private String deliveryType;
	
	@JsonProperty("countryId")
	private String countryId;
	
	@JsonProperty("storeId")
	private String storeId;
	

}
