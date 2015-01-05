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
import com.vpmsbcm.common.model.NormalRocket;
import com.vpmsbcm.common.model.OrderRocket;
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
	public Rocket eventListener(OrderRocket rocket) {
		log.info("received orderRocket=" + rocket);

		testRocket(rocket);

		if (rocket.getState().equals(State.CLASS_B)) {
			return new NormalRocket(rocket.getWood(), rocket.getDetonator(), rocket.getCharges(), rocket.getChargeAmount(), rocket.getLoades(), rocket.getProducerID());
		}

		return rocket;
	}

	@SpaceDataEvent
	@Transactional
	public Rocket eventListener(NormalRocket event) {
		log.info("received rocket=" + event);
		return testRocket(event);
	}

	private boolean testLoadsWorking(List<Load> loads) {
		for (Load load : loads) {
			if (load.getDefect()) {
				return false;
			}
		}
		return true;
	}

	private Rocket testRocket(Rocket rocket) {
		rocket.setControllerID(id);

		if (!testLoadsWorking(rocket.getLoades())) {
			rocket.setState(State.DEFECT);
			return rocket;
		}
		if (rocket.getChargeAmount() >= 130) {
			rocket.setState(State.CLASS_A);
			return rocket;
		}
		if (rocket.getChargeAmount() >= 120) {
			rocket.setState(State.CLASS_B);
			return rocket;
		}
		rocket.setState(State.DEFECT);
		return rocket;
	}
}
