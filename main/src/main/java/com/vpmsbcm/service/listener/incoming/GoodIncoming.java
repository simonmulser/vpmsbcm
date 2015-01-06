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
import com.vpmsbcm.common.model.Detonator;
import com.vpmsbcm.common.model.Good;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.Wood;
import com.vpmsbcm.service.Warehouse;

@EventDriven
@Notify(gigaSpace = "warehouseSpace")
@NotifyType(write = true)
public class GoodIncoming {

	@Autowired
	private Warehouse warehouse;

	final Logger log = LoggerFactory.getLogger(GoodIncoming.class);

	public GoodIncoming() {
	}

	@EventTemplate
	Good unprocessedData() {
		Good template = new Good();
		return template;
	}

	@SpaceDataEvent
	public Charge eventListener(Charge event) {
		warehouse.newPropellingCharge(event);
		return null;
	}

	@SpaceDataEvent
	public Detonator eventListener(Detonator event) {
		warehouse.updateCaseAndDetonator(1);
		return null;
	}

	@SpaceDataEvent
	public Wood eventListener(Wood event) {
		warehouse.updateWoodstick(1);
		return null;
	}

	@SpaceDataEvent
	public Load eventListener(Load event) {
		warehouse.updateLoad(1);
		return null;
	}
}
