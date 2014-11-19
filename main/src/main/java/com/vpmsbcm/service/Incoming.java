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

import com.vpmsbcm.common.model.CaseWithDetonator;
import com.vpmsbcm.common.model.Good;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.PropellingCharge;
import com.vpmsbcm.common.model.Woodstick;

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
		if (event instanceof Woodstick)
			warehouse.updateWoodstick(1);
		if (event instanceof Load)
			warehouse.updateLoad(1);
		if (event instanceof PropellingCharge)
			warehouse.newPropellingCharge((PropellingCharge) event);
		if (event instanceof CaseWithDetonator)
			warehouse.updateCaseAndDetonator(1);
		return null;
	}
}
