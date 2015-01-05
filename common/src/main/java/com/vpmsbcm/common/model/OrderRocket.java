package com.vpmsbcm.common.model;

import java.util.List;

public class OrderRocket extends Rocket {

	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderRocket() {
		super();
	}

	public OrderRocket(Wood wood, Detonator detonator, List<Charge> charges, Integer chargeAmount, List<Load> loades, Integer producerID, String orderId) {
		super(wood, detonator, charges, chargeAmount, loades, producerID);
		this.orderId = orderId;
	}
}
