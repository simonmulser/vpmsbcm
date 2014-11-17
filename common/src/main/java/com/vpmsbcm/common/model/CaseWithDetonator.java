package com.vpmsbcm.common.model;

import java.util.concurrent.atomic.AtomicInteger;

import com.gigaspaces.annotation.pojo.SpaceId;

public class CaseWithDetonator extends Good {

	private static AtomicInteger ID = new AtomicInteger(1);

	private Integer id;
	private String supplier;

	public CaseWithDetonator() {
	}

	public CaseWithDetonator(String supplier) {
		this.id = ID.incrementAndGet();
		this.supplier = supplier;
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

	@Override
	public String toString() {
		return "CaseWithDetonator [id=" + id + ", supplier=" + supplier + "]";
	}

}