package com.vpmsbcm.quality;

import java.util.List;

import javax.annotation.PostConstruct;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;
import org.openspaces.events.polling.ReceiveHandler;
import org.openspaces.events.polling.receive.ReceiveOperationHandler;
import org.openspaces.events.polling.receive.SingleTakeReceiveOperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.vpmsbcm.common.model.IDFactory;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.State;

@EventDriven
@Polling
public class Quality {

	final Logger log = LoggerFactory.getLogger(Quality.class);

	private int id;

	@GigaSpaceContext
	private GigaSpace gigaSpace;

	public Quality() {
	}

	@PostConstruct
	public void init() {
		setId();
	}

	private void setId() {
		IDFactory factory = new IDFactory();
		factory.setId(1);

		factory = gigaSpace.take(factory, 1000);
		if (factory != null) {
			id = factory.getIdQuality();
			factory.setIdQuality(id + 1);
			gigaSpace.write(factory);
			log.info("started quality with id=" + id);
		} else {
			id = -1;
		}
	}

	@ReceiveHandler
	ReceiveOperationHandler receiveHandler() {
		SingleTakeReceiveOperationHandler receiveHandler = new SingleTakeReceiveOperationHandler();
		return receiveHandler;
	}

	@EventTemplate
	Rocket template() {
		Rocket template = new Rocket();
		template.setState(State.NOT_TESTED);
		return template;
	}

	@SpaceDataEvent
	@Transactional
	public Rocket eventListener(Rocket event) {
		log.info("received rocket=" + event);
		if (testLoadsWorking(event.getLoades()) && event.getChargeAmount() >= 120) {
			event.setState(State.CLASS_A);
		} else {
			event.setState(State.DEFECT);
		}
		event.setControllerID(id);
		return event;
	}

	private boolean testLoadsWorking(List<Load> loads) {
		for (Load load : loads) {
			if (load.getDefect()) {
				return false;
			}
		}
		return true;
	}
}
