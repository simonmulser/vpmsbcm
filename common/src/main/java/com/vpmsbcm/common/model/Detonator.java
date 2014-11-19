package com.vpmsbcm.common.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import com.gigaspaces.annotation.pojo.SpaceId;

public class Detonator extends Good implements Serializable {

	private Integer id;

	private static AtomicInteger ID = new AtomicInteger(0);

	private String supplier;

	public Detonator() {
	}

	public Detonator(String supplier) {
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
		return "Detonator [id=" + id + ", supplier=" + supplier + "]";
	}

	@SpaceId(autoGenerate = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
