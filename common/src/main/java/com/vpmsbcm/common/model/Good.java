package com.vpmsbcm.common.model;

import com.gigaspaces.annotation.pojo.SpaceId;

public class Good {

	protected Integer id;

	public Good() {
	}

	@SpaceId(autoGenerate = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
