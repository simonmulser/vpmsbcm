package com.vpmsbcm.service;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpmsbcm.common.model.Charge;
import com.vpmsbcm.common.model.Detonator;
import com.vpmsbcm.common.model.Good;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.Wood;

@EventDriven
@Notify(gigaSpace = "warehouseSpace")
@NotifyType(take = true)
public class Outgoing {

	@Autowired
	private Warehouse warehouse;

	final Logger log = LoggerFactory.getLogger(Outgoing.class);

	public Outgoing() {
	}

	@EventTemplate
	Good unprocessedData() {
		Good template = new Good();
		return template;
	}

	@SpaceDataEvent
	public Good eventListener(Good event) {
		if (event instanceof Wood)
			warehouse.updateWoodstick(-1);
		if (event instanceof Load)
			warehouse.updateLoad(-1);
		if (event instanceof Charge)
			warehouse.removeCharge((Charge) event);
		if (event instanceof Detonator)
			warehouse.updateCaseAndDetonator(-1);
		if (event instanceof Rocket)
			warehouse.removeRocket((Rocket) event);
		return null;
	}
}
