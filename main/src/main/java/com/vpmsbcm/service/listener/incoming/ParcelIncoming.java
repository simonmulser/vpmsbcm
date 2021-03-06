package com.vpmsbcm.service.listener.incoming;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpmsbcm.common.model.Parcel;
import com.vpmsbcm.service.Warehouse;

@EventDriven
@Notify(gigaSpace = "warehouseSpace")
@NotifyType(write = true)
public class ParcelIncoming {

	@Autowired
	private Warehouse warehouse;

	final Logger log = LoggerFactory.getLogger(ParcelIncoming.class);

	public ParcelIncoming() {
	}

	@EventTemplate
	Parcel unprocessedData() {
		Parcel template = new Parcel();
		return template;
	}

	@SpaceDataEvent
	public Parcel eventListener(Parcel event) {
		warehouse.addParcel(event);
		return null;
	}
}
