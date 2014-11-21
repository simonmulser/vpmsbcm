package com.vpmsbcm.exporter;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.State;

@EventDriven
@Polling(gigaSpace = "warehouseSpace")
public class Destroyer {

	final Logger log = LoggerFactory.getLogger(Destroyer.class);

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouseSpace;

	@GigaSpaceContext(name = "trashSpace")
	private GigaSpace trashSpace;

	public Destroyer() {
	}

	@EventTemplate
	Rocket template() {
		Rocket template = new Rocket();
		template.setState(State.DEFECT);
		return template;
	}

	@SpaceDataEvent
	public Rocket eventListener(Rocket event) {
		log.info("received rocket=" + event);
		event.setState(State.DESTROYED);

		trashSpace.write(event);
		return null;
	}
}
