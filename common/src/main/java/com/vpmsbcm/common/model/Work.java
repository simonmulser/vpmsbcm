package com.vpmsbcm.common.model;

import com.gigaspaces.annotation.pojo.SpaceId;

public class Work {

	public Work() {
	}

	protected String id;

	@SpaceId(autoGenerate = true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Work [id=" + id + "]";
	}

}
