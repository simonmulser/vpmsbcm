package com.vpmsbcm.service;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.Charge;
import com.vpmsbcm.common.model.Detonator;
import com.vpmsbcm.common.model.Good;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.Wood;

@Component
@EventDriven
@Notify
@NotifyType(write = true)
public class Incoming {

	@Autowired
	private Warehouse warehouse;

	final Logger log = LoggerFactory.getLogger(Incoming.class);

	public Incoming() {
	}

	@EventTemplate
	Good unprocessedData() {
		Good template = new Good();
		return template;
	}

	@SpaceDataEvent
	public Good eventListener(Good event) {
		log.info("received good" + event);
		if (event instanceof Wood)
			warehouse.updateWoodstick(1);
		if (event instanceof Load)
			warehouse.updateLoad(1);
		if (event instanceof Charge)
			warehouse.newPropellingCharge((Charge) event);
		if (event instanceof Detonator)
			warehouse.updateCaseAndDetonator(1);
		if (event instanceof Rocket)
			warehouse.addRocket((Rocket) event);
		return null;
	}
}
