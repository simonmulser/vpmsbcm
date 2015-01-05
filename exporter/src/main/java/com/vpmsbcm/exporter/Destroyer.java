package com.vpmsbcm.exporter;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.TransactionalEvent;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;
import org.openspaces.events.polling.ReceiveHandler;
import org.openspaces.events.polling.receive.ReceiveOperationHandler;
import org.openspaces.events.polling.receive.SingleTakeReceiveOperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpmsbcm.common.model.NormalRocket;
import com.vpmsbcm.common.model.State;

@TransactionalEvent
@EventDriven
@Polling(gigaSpace = "warehouseSpace")
public class Destroyer {

	final Logger log = LoggerFactory.getLogger(Destroyer.class);

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouseSpace;

	@GigaSpaceContext(name = "trashSpace")
	private GigaSpace trashSpace;

	@Autowired
	Supervisor supervisor;

	public Destroyer() {
	}

	@EventTemplate
	NormalRocket template() {
		NormalRocket template = new NormalRocket();
		template.setState(State.DEFECT);
		return template;
	}

	@ReceiveHandler
	ReceiveOperationHandler receiveHandler() {
		SingleTakeReceiveOperationHandler handler = new SingleTakeReceiveOperationHandler();
		handler.setUseFifoGrouping(true);
		return handler;
	}

	@SpaceDataEvent
	public NormalRocket eventListener(NormalRocket event) {
		log.info("received rocket=" + event);
		event.setState(State.DESTROYED);
		event.setExporterID(supervisor.getId());
		trashSpace.write(event);
		return null;
	}
}
