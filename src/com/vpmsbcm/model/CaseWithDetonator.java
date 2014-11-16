package com.vpmsbcm.model;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.metadata.index.SpaceIndexType;

public class CaseWithDetonator {
	private static int ID = 1;

    private Integer id;
    private String supplier;

    public CaseWithDetonator() {}

    public CaseWithDetonator(String supplier) {
        this.id = ID++;
        this.supplier = supplier;
    }

	public String getLieferant() {
		return supplier;
	}

	public void setLieferant(String supplier) {
		this.supplier = supplier;
	}


	@Override
	public String toString() {
		return "CaseWithDetonator [id=" + id + ", supplier=" + supplier + "]";
	}
	
}