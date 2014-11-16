package com.vpmsbcm.model;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.metadata.index.SpaceIndexType;

public class Load {

	private static int ID = 1;

	private Integer id;
	private String supplier;
	private Boolean defect;

	public Load() {
	}

	public Load(String supplier, Boolean defect) {
		this.id = ID++;
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