package com.tesco.fulfillment.tibco.messaging.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LineItem {
	
	private String timestamp;
	private String orderlineID;
	private String orderedProductTpnb;
	private String pickedProductTpnb;
	private String plannedQty;
	private String pickedQty;
	private String status;
	private String reasonCode;
}
