package com.vpmsbcm.service;

import org.openspaces.events.adapter.SpaceDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.CaseWithDetonator;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.PropellingCharge;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.Woodstick;

@Component
public class Incoming {

	@Autowired
	private Warehouse warehouse;

	final Logger log = LoggerFactory.getLogger(Incoming.class);

	public Incoming() {
	}

	@SpaceDataEvent
	public Woodstick eventListener(Woodstick event) {
		warehouse.updateWoodstick(1);
		return event;
	}

	@SpaceDataEvent
	public CaseWithDetonator eventListener(CaseWithDetonator event) {
		warehouse.updateCaseAndDetonator(1);
		return event;
	}

	@SpaceDataEvent
	public Load eventListener(Load event) {
		warehouse.updateLoad(1);
		return event;
	}

	@SpaceDataEvent
	public PropellingCharge eventListener(PropellingCharge event) {
		warehouse.newPropellingCharge(event);
		return event;
	}

	@SpaceDataEvent
	public Rocket eventListener(Rocket event) {
		warehouse.addRocket(event);
		return event;
	}
}
