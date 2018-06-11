package com.tesco.fulfillment.tibco.messaging.vo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.tesco.fulfillment.tibco.messaging.vo.LineItem;
import com.tesco.fulfillment.tibco.messaging.vo.MessagePayload;

@RunWith(MockitoJUnitRunner.class)
public class MessagePayloadTest {
	
	@InjectMocks
	private MessagePayload messagePayload;
	
	@Before
	public void init(){
		LineItem lineItem = new LineItem("2016-07-14T09:10:00.6212Z", "1", "073015194", "073015194", "1", 
				"1", "Success", "NA");
		List<LineItem> lineItemsList = new ArrayList<>();
		lineItemsList.add(lineItem);
		
		messagePayload = new MessagePayload("9TES27860000002", "OFS:GMO:XYZ99-4", "2786",  null, null, lineItemsList,"PT_2212_61da1a10",
				"Store_Collect","UK","2212");	
	}
	
	@Test
	public void testGetpickedPackageID() {
		Assert.assertEquals("9TES27860000002", messagePayload.getPickedPackageID());
	}


	@Test
	public void testGetFulFillmentOrderId() {
		Assert.assertEquals("OFS:GMO:XYZ99-4", messagePayload.getFulfilmentOrderID());
	}

	@Test
	public void testGetFulFillmentNodeId() {
		Assert.assertEquals("2786", messagePayload.getFulfillmentNodeID());
	}

	@Test
	public void testGetTimeStamp() {
		Assert.assertEquals("2016-07-14T09:10:00.6212Z", messagePayload.getItems().get(0).getTimestamp());
	}
	
	@Test
	public void testGetOrderLineId() {
		Assert.assertEquals("1", messagePayload.getItems().get(0).getOrderlineID());
	}
	
	@Test
	public void testGetOrderedPdtTpnb() {
		Assert.assertEquals("073015194", messagePayload.getItems().get(0).getOrderedProductTpnb());
	}
	
	
	@Test
	public void testGetPickedPdtTpnb() {
		Assert.assertEquals("073015194", messagePayload.getItems().get(0).getPickedProductTpnb());
	}

	@Test
	public void testGetPlannedQty() {
		Assert.assertEquals("1", messagePayload.getItems().get(0).getPlannedQty());
	}
	
	@Test
	public void testGetPickedQty() {
		Assert.assertEquals("1", messagePayload.getItems().get(0).getPickedQty());
	}

	@Test
	public void testGetStatus() {
		Assert.assertEquals("Success", messagePayload.getItems().get(0).getStatus());
	}
	
	@Test
	public void testGetReasonCode() {
		Assert.assertEquals("NA", messagePayload.getItems().get(0).getReasonCode());
	}
	
	@Test
	public void testGetPickTripId() {
		Assert.assertEquals("PT_2212_61da1a10", messagePayload.getPicktripId());

	}
	
	@Test
	public void testGetDeliveryType() {
		Assert.assertEquals("Store_Collect", messagePayload.getDeliveryType());

	}

	@Test
	public void testGetCountryId() {
		Assert.assertEquals("UK", messagePayload.getCountryId());

	}

	
	@Test
	public void testGetStoreId() {
		Assert.assertEquals("2212", messagePayload.getStoreId());

	}
		
	@Test
	public void testToString() {
		Assert.assertEquals("MessagePayload(pickedPackageID=9TES27860000002, fulfilmentOrderID=OFS:GMO:XYZ99-4, fulfillmentNodeID=2786, location=null, timestamp=null, items=[LineItem(timestamp=2016-07-14T09:10:00.6212Z, orderlineID=1, orderedProductTpnb=073015194, pickedProductTpnb=073015194, plannedQty=1, pickedQty=1, status=Success, reasonCode=NA)], picktripId=PT_2212_61da1a10, deliveryType=Store_Collect, countryId=UK, storeId=2212)", messagePayload.toString());
	}
}
