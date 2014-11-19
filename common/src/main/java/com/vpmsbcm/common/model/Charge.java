package com.vpmsbcm.common.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import com.gigaspaces.annotation.pojo.SpaceId;

public class Charge extends Good implements Serializable {

	private Integer id;

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

	public Charge(Integer id, String supplier, int amount) {
		this.id = id;
		this.supplier = supplier;
		this.amount = amount;
	}

	@SpaceId(autoGenerate = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		return "Charge [supplier=" + supplier + ", amount=" + amount + "]";
	}
}
