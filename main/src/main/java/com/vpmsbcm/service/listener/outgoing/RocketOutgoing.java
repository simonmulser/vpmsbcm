package com.vpmsbcm.service.listener.outgoing;

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
@NotifyType(take = true)
public class RocketOutgoing {

	@Autowired
	private Warehouse warehouse;

	final Logger log = LoggerFactory.getLogger(RocketOutgoing.class);

	public RocketOutgoing() {
	}

	@EventTemplate
	Rocket unprocessedData() {
		Rocket template = new Rocket();
		return template;
	}

	@SpaceDataEvent
	public Rocket eventListener(Rocket event) {
		warehouse.removeRocket(event);
		return null;
	}
}
