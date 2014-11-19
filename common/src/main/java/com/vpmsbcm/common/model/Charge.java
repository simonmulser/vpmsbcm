package com.vpmsbcm.common.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Charge extends Good {

	private static AtomicInteger ID = new AtomicInteger(0);

	private String supplier;

	private Integer amount;

	public Charge() {
	}

	public Charge(String supplier) {
		this.id = ID.incrementAndGet();
		this.supplier = supplier;
		this.amount = 500;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "PropellingCharge [supplier=" + supplier + ", amount=" + amount + "]";
	}
}