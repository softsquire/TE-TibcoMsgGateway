package com.tesco.fulfillment.tibco.messaging.vo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.tesco.fulfillment.tibco.messaging.vo.LineItem;

@RunWith(MockitoJUnitRunner.class)
public class LineItemTest {
	
	@InjectMocks
    private LineItem lineItem;
	
	@Before
	public void init(){
		 lineItem = new LineItem("2016-07-14T09:10:00.6212Z", "1", "073015194", "073015194", "1", 
				"1", "Success", "NA");
	}
	
	@Test
	public void testGetTimeStamp() {
		Assert.assertEquals("2016-07-14T09:10:00.6212Z", lineItem.getTimestamp());
	}
	
	@Test
	public void testGetOrderLineId() {
		Assert.assertEquals("1", lineItem.getOrderlineID());
	}
	
	@Test
	public void testGetOrderedPdtTpnb() {
		Assert.assertEquals("073015194", lineItem.getOrderedProductTpnb());
	}
	
	
	@Test
	public void testGetPickedPdtTpnb() {
		Assert.assertEquals("073015194", lineItem.getPickedProductTpnb());
	}

	@Test
	public void testGetPlannedQty() {
		Assert.assertEquals("1", lineItem.getPlannedQty());
	}
	
	@Test
	public void testGetPickedQty() {
		Assert.assertEquals("1",lineItem.getPickedQty());
	}

	@Test
	public void testGetStatus() {
		Assert.assertEquals("Success", lineItem.getStatus());
	}
	
	@Test
	public void testGetReasonCode() {
		Assert.assertEquals("NA", lineItem.getReasonCode());
	}
	
	@Test
	public void testToString() {
		Assert.assertEquals("LineItem(timestamp=2016-07-14T09:10:00.6212Z, orderlineID=1, orderedProductTpnb=073015194, pickedProductTpnb=073015194, "
				+ "plannedQty=1, pickedQty=1, status=Success, reasonCode=NA)", lineItem.toString());
	}
}
