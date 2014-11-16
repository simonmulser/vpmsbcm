package com.vpmsbcm.model;


import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.metadata.index.SpaceIndexType;

public class CaseWithDetonator implements IOrderable{
	private static int ID = 1;

    private Integer id;
    private String supplier;

    public CaseWithDetonator() {}

    public CaseWithDetonator(String supplier) {
        this.id = ID++;
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