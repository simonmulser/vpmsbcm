package com.vpmsbcm.service.listener.outgoing;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpmsbcm.common.model.Charge;
import com.vpmsbcm.service.Warehouse;

@EventDriven
@Notify(gigaSpace = "warehouseSpace")
@NotifyType(take = true)
public class ChargeOutgoing {

	@Autowired
	private Warehouse warehouse;

	final Logger log = LoggerFactory.getLogger(ChargeOutgoing.class);

	public ChargeOutgoing() {
	}

	@EventTemplate
	Charge unprocessedData() {
		Charge template = new Charge();
		return template;
	}

	@SpaceDataEvent
	public Charge eventListener(Charge event) {
		warehouse.removeCharge(event);
		return null;
	}
}
