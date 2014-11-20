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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Charge other = (Charge) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
