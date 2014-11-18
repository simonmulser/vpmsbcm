package com.vpmsbcm.service;

import java.util.HashMap;

import javax.annotation.PostConstruct;

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
import com.vpmsbcm.gui.WarehousePanel;

@Component
public class Incoming {

	@Autowired
	private WarehousePanel warehousePanel;

	final Logger log = LoggerFactory.getLogger(Incoming.class);

	private HashMap<Integer, PropellingCharge> cases;

	public Incoming() {
	}

	@PostConstruct
	public void init() {
		cases = new HashMap<Integer, PropellingCharge>();
	}

	@SpaceDataEvent
	public synchronized Woodstick eventListener(Woodstick event) {
		warehousePanel.updateWoodstick(1);
		return event;
	}

	@SpaceDataEvent
	public synchronized CaseWithDetonator eventListener(CaseWithDetonator event) {
		warehousePanel.updateCaseAndDetonator(1);
		return event;
	}

	@SpaceDataEvent
	public synchronized Load eventListener(Load event) {
		warehousePanel.updateLoad(1);
		return event;
	}

	@SpaceDataEvent
	public synchronized PropellingCharge eventListener(PropellingCharge event) {
		warehousePanel.updatePropellingCharge(1);
		return event;
	}

	@SpaceDataEvent
	public synchronized Rocket eventListener(Rocket event) {
		// warehousePanel.updatePropellingCharge(1);
		return event;
	}
}
