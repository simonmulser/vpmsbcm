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

	@Override
	public String toString() {
		return "OrderRocket [orderId=" + orderId + ", id=" + id + ", wood=" + wood + ", detonator=" + detonator + ", charges=" + charges + ", loades=" + loades + ", chargeAmount="
				+ chargeAmount + ", state=" + state + ", producerID=" + producerID + ", controllerID=" + controllerID + ", exporterID=" + exporterID + "]";
	}

	public Rocket degrade() {
		NormalRocket rocket = new NormalRocket();
		rocket.setId(id);
		rocket.setWood(wood);
		rocket.setDetonator(detonator);
		rocket.setCharges(charges);
		rocket.setLoades(loades);

		rocket.setChargeAmount(chargeAmount);
		rocket.setState(state);
		rocket.setProducerID(producerID);
		rocket.setControllerID(controllerID);
		rocket.setExporterID(exporterID);
		return rocket;
	}
}
