package com.vpmsbcm.service.listener.incoming;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpmsbcm.common.model.NormalRocket;
import com.vpmsbcm.service.Warehouse;

@EventDriven
@Notify(gigaSpace = "trashSpace")
@NotifyType(write = true)
public class DestroyedRocketsIncoming {

	@Autowired
	private Warehouse warehouse;

	final Logger log = LoggerFactory.getLogger(DestroyedRocketsIncoming.class);

	public DestroyedRocketsIncoming() {
	}

	@EventTemplate
	NormalRocket unprocessedData() {
		NormalRocket template = new NormalRocket();
		return template;
	}

	@SpaceDataEvent
	public NormalRocket eventListener(NormalRocket event) {
		warehouse.addDestroyedRocket(event);
		return null;
	}
}
