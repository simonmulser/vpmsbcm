package com.vpmsbcm.common.model;

import java.util.concurrent.atomic.AtomicInteger;

import com.gigaspaces.annotation.pojo.SpaceId;

public class Load extends Good {

	private static AtomicInteger ID = new AtomicInteger(0);

	private Integer id;
	private String supplier;
	private Boolean defect;

	public Load() {
	}

	public Load(String supplier, Boolean defect) {
		this.id = ID.incrementAndGet();
		this.supplier = supplier;
		this.defect = defect;
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
		return "Load [id=" + id + ", supplier=" + supplier + "]";
	}

}