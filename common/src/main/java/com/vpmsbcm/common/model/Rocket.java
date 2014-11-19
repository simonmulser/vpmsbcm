package com.vpmsbcm.common.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Rocket extends Good {

	private Wood wood;
	private Detonator detonator;
	private List<Charge> charges;
	private List<Load> loades;

	private Integer chargeAmount;

	public static AtomicInteger ID = new AtomicInteger(0);

	public Rocket() {
	}

	public Rocket(Wood wood, Detonator detonator, List<Charge> charges, Integer chargeAmount, List<Load> loades) {
		super();
		id = ID.incrementAndGet();
		this.wood = wood;
		this.detonator = detonator;
		this.charges = charges;
		this.chargeAmount = chargeAmount;
		this.loades = loades;
	}

	public Wood getWood() {
		return wood;
	}

	public void setWood(Wood wood) {
		this.wood = wood;
	}

	public Detonator getDetonator() {
		return detonator;
	}

	public void setDetonator(Detonator detonator) {
		this.detonator = detonator;
	}

	public List<Charge> getCharges() {
		return charges;
	}

	public void setCharges(List<Charge> charges) {
		this.charges = charges;
	}

	public List<Load> getLoades() {
		return loades;
	}

	public void setLoades(List<Load> loades) {
		this.loades = loades;
	}

	public Integer getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Integer chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	@Override
	public String toString() {
		return "Rocket [wood=" + wood + ", detonator=" + detonator + ", charges=" + charges + ", loades=" + loades + ", chargeAmount=" + chargeAmount + "]";
	}
}
