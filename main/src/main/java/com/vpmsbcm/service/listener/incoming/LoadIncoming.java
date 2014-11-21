package com.vpmsbcm.service.listener.incoming;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.service.Warehouse;

@EventDriven
@Notify(gigaSpace = "warehouseSpace")
@NotifyType(write = true)
public class LoadIncoming {

	@Autowired
	private Warehouse warehouse;

	final Logger log = LoggerFactory.getLogger(LoadIncoming.class);

	public LoadIncoming() {
	}

	@EventTemplate
	Load unprocessedData() {
		Load template = new Load();
		return template;
	}

	@SpaceDataEvent
	public Load eventListener(Load event) {
		warehouse.updateLoad(1);
		return null;
	}
}
