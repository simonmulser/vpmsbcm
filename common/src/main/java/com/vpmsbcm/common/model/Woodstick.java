package com.vpmsbcm.common.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Woodstick extends Good {

	private static AtomicInteger ID = new AtomicInteger(0);

	private String supplier;

	public Woodstick() {
	}

	public Woodstick(String supplier) {
		this.id = ID.incrementAndGet();
		this.supplier = supplier;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "Woodstick [id=" + id + ", supplier=" + supplier + "]";
	}

}