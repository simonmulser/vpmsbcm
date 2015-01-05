package com.vpmsbcm.common.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import com.gigaspaces.annotation.pojo.SpaceId;

public class Load extends Good implements Serializable {

	protected Integer id;

	private static AtomicInteger ID = new AtomicInteger(0);

	private String supplier;
	private Boolean defect;
	private Color color;

	public Load() {
	}

	public Load(String supplier, Boolean defect, Color color) {
		this.id = ID.incrementAndGet();
		this.supplier = supplier;
		this.defect = defect;
		this.color = color;
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

	public Boolean getDefect() {
		return defect;
	}

	public void setDefect(Boolean defect) {
		this.defect = defect;
	}

	@Override
	public String toString() {
		return "Load [id=" + id + ", supplier=" + supplier + ", defect=" + defect + "]";
	}

}
