package com.vpmsbcm.service.listener.incoming;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.service.Warehouse;

@EventDriven
@Notify(gigaSpace = "warehouseSpace")
@NotifyType(write = true)
public class RocketIncoming {

	@Autowired
	private Warehouse warehouse;

	final Logger log = LoggerFactory.getLogger(RocketIncoming.class);

	public RocketIncoming() {
	}

	@EventTemplate
	Rocket unprocessedData() {
		Rocket template = new Rocket();
		return template;
	}

	@SpaceDataEvent
	public Rocket eventListener(Rocket event) {
		warehouse.addRocket(event);
		return null;
	}
}
