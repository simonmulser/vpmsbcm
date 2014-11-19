package com.vpmsbcm.producer;

import java.util.Random;

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

import com.j_spaces.core.client.SQLQuery;
import com.vpmsbcm.common.model.CaseWithDetonator;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.PropellingCharge;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.Woodstick;
import com.vpmsbcm.common.model.Work;

@EventDriven
@Polling
public class Producer {

	final Logger log = LoggerFactory.getLogger(Producer.class);

	@GigaSpaceContext
	private GigaSpace gigaSpace;

	public Producer() {
	}

	@ReceiveHandler
	ReceiveOperationHandler receiveHandler() {
		SingleTakeReceiveOperationHandler receiveHandler = new SingleTakeReceiveOperationHandler();
		return receiveHandler;
	}

	@EventTemplate
	Work template() {
		Work template = new Work();
		return template;
	}

	@SpaceDataEvent
	@Transactional
	public Work eventListener(Work event) {
		log.info("received work=" + event);
		produce();
		return null;
	}

	private void produce() {
		int chargeNeeded = calculateCharge();
		log.debug("chargeNeeded=" + chargeNeeded);

		Woodstick woodstick = gigaSpace.take(new Woodstick());
		if (woodstick == null) {
			log.info("not enough woodsticks!");
			throw new RuntimeException();

		}

		CaseWithDetonator detonator = gigaSpace.take(new CaseWithDetonator());
		if (detonator == null) {
			log.info("not enough detonators");
			throw new RuntimeException();

		}

		Load[] load = gigaSpace.takeMultiple(new Load(), 3);
		if (load.length != 3) {
			log.info(gigaSpace.getCurrentTransaction() + "");

			log.info("not 3 loads");
			throw new RuntimeException();
		}

		// TODO maybe work with change
		PropellingCharge charge = gigaSpace.take(new SQLQuery<PropellingCharge>(PropellingCharge.class, "amount < 500"));
		while (charge != null) {
			if (charge.getAmount() <= chargeNeeded) {
				chargeNeeded = chargeNeeded - charge.getAmount();
			} else {
				charge.setAmount(charge.getAmount() - chargeNeeded);
				gigaSpace.write(charge);
				createRocket();
				return;
			}
			charge = gigaSpace.take(new SQLQuery<PropellingCharge>(PropellingCharge.class, "amount < 500"));
		}

		charge = gigaSpace.take(new SQLQuery<PropellingCharge>(PropellingCharge.class, "amount = 500"));
		if (charge == null) {
			log.info("not enough charge");
			throw new RuntimeException();

		}

		charge.setAmount(charge.getAmount() - chargeNeeded);
		gigaSpace.write(charge);
		createRocket();
	}

	private void createRocket() {
		Rocket rocket = new Rocket();
		rocket.setId(Rocket.ID.incrementAndGet());

		gigaSpace.write(rocket);
		log.info("created rocket=" + rocket);
	}

	private int calculateCharge() {
		int randomNumber = (int) Math.round(new Random().nextDouble() * 30);
		return 130 + (randomNumber - 15);
	}
}
