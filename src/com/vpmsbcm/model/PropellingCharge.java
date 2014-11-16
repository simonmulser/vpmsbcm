package com.vpmsbcm.model;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.metadata.index.SpaceIndexType;

public class PropellingCharge {
	
	private static int ID = 1;

    private Integer id;
    private String supplier;
    
    private Integer amount;

    public PropellingCharge() {}

    public PropellingCharge(String supplier) {
        this.id = ID++;
        this.supplier = supplier;
        this.amount = 500;
    }

	public String getLieferant() {
		return supplier;
	}

	public void setLieferant(String supplier) {
		this.supplier = supplier;
	}
	
    @SpaceId(autoGenerate=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PropellingCharge [id=" + id + ", supplier=" + supplier + "]";
	}
	
}