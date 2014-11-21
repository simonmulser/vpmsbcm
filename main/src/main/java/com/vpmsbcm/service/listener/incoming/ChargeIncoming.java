package com.vpmsbcm.service.listener.incoming;

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
@NotifyType(write = true)
public class ChargeIncoming {

	@Autowired
	private Warehouse warehouse;

	final Logger log = LoggerFactory.getLogger(ChargeIncoming.class);

	public ChargeIncoming() {
	}

	@EventTemplate
	Charge unprocessedData() {
		Charge template = new Charge();
		return template;
	}

	@SpaceDataEvent
	public Charge eventListener(Charge event) {
		warehouse.newPropellingCharge(event);
		return null;
	}
}
