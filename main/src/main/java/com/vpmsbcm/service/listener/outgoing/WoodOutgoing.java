package com.vpmsbcm.service.listener.outgoing;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpmsbcm.common.model.Wood;
import com.vpmsbcm.service.Warehouse;

@EventDriven
@Notify(gigaSpace = "warehouseSpace")
@NotifyType(take = true)
public class WoodOutgoing {

	@Autowired
	private Warehouse warehouse;

	final Logger log = LoggerFactory.getLogger(WoodOutgoing.class);

	public WoodOutgoing() {
	}

	@EventTemplate
	Wood unprocessedData() {
		Wood template = new Wood();
		return template;
	}

	@SpaceDataEvent
	public Wood eventListener(Wood event) {
		warehouse.updateWoodstick(-1);
		return null;
	}
}
