package com.vpmsbcm.common.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.gigaspaces.annotation.pojo.SpaceId;

public class Parcel {

	private List<Rocket> rockets;

	private static AtomicInteger ID = new AtomicInteger(0);

	private Integer id;

	public Parcel() {
	}

	public Parcel(List<Rocket> rockets) {
		this.rockets = rockets;
		this.id = ID.incrementAndGet();
	}

	@SpaceId(autoGenerate = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Rocket> getRockets() {
		return rockets;
	}

	public void setRockets(List<Rocket> rockets) {
		this.rockets = rockets;
	}

}
