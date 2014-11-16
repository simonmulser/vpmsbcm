package com.vpmsbcm.model;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.metadata.index.SpaceIndexType;

public class Woodstick {
	
	private static int ID = 1;

    private Integer id;
    private String supplier;

    public Woodstick() {}

    public Woodstick(String supplier) {
        this.id = ID++;
        this.supplier = supplier;
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
		return "Woodstick [id=" + id + ", supplier=" + supplier + "]";
	}
	
}