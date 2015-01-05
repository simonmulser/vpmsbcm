package com.vpmsbcm.common.model;

import com.gigaspaces.annotation.pojo.SpaceId;

public class IDFactory {

	private Integer id;
	private Integer idProducer;
	private Integer idQuality;
	private Integer idExporter;
	private Integer idOrder;

	public IDFactory() {
	}

	public void init() {
		id = 1;
		idProducer = 1;
		idQuality = 1;
		idExporter = 1;
		idOrder = 1;
	}

	@SpaceId(autoGenerate = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdProducer() {
		return idProducer;
	}

	public void setIdProducer(Integer idProducer) {
		this.idProducer = idProducer;
	}

	public Integer getIdQuality() {
		return idQuality;
	}

	public void setIdQuality(Integer idQuality) {
		this.idQuality = idQuality;
	}

	public Integer getIdExporter() {
		return idExporter;
	}

	public void setIdExporter(Integer idExporter) {
		this.idExporter = idExporter;
	}

	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}

}
