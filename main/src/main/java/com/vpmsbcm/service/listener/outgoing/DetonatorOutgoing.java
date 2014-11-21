package com.vpmsbcm.service.listener.outgoing;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpmsbcm.common.model.Detonator;
import com.vpmsbcm.service.Warehouse;

@EventDriven
@Notify(gigaSpace = "warehouseSpace")
@NotifyType(take = true)
public class DetonatorOutgoing {

	@Autowired
	private Warehouse warehouse;

	final Logger log = LoggerFactory.getLogger(DetonatorOutgoing.class);

	public DetonatorOutgoing() {
	}

	@EventTemplate
	Detonator unprocessedData() {
		Detonator template = new Detonator();
		return template;
	}

	@SpaceDataEvent
	public Detonator eventListener(Detonator event) {
		warehouse.updateCaseAndDetonator(-1);
		return null;
	}
}
