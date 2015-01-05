package com.vpmsbcm.common.model;

import java.util.List;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.vpmsbcm.common.util.Util;

public class Parcel {

	private List<NormalRocket> rockets;

	private String id;

	public Parcel() {
	}

	public Parcel(List<NormalRocket> rockets) {
		this.rockets = rockets;
	}

	@SpaceId(autoGenerate = true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<NormalRocket> getRockets() {
		return rockets;
	}

	public void setRockets(List<NormalRocket> rockets) {
		this.rockets = rockets;
	}

	@Override
	public String toString() {
		return "Parcel [rockets=" + rockets + ", id=" + Util.splitId(id) + "]";
	}

}
