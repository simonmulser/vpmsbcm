package com.vpmsbcm.service;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpmsbcm.common.model.Good;

@EventDriven
@Notify
@NotifyType(write = true)
public class IncomingListener {

	final Logger log = LoggerFactory.getLogger(IncomingListener.class);

	@Autowired
	private Warehouse warehouse;

	public IncomingListener() {
	}

	@EventTemplate
	Good unprocessedData() {
		return null;
	}

	@SpaceDataEvent
	public Good eventListener(Good good) {
		warehouse.arrived(good);
		log.info("arrived " + good);
		return good;
	}

}