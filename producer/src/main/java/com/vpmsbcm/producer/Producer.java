package com.vpmsbcm.producer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
import com.vpmsbcm.common.model.Charge;
import com.vpmsbcm.common.model.Detonator;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.Wood;
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

		Wood wood = gigaSpace.take(new Wood());
		if (wood == null) {
			log.info("not enough woodsticks!");
			throw new RuntimeException();

		}

		Detonator detonator = gigaSpace.take(new Detonator());
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
		LinkedList<Charge> chargesUsed = new LinkedList<Charge>();
		Charge charge = gigaSpace.take(new SQLQuery<Charge>(Charge.class, "amount < 500"));
		while (charge != null) {
			if (charge.getAmount() <= chargeNeeded) {
				chargeNeeded = chargeNeeded - charge.getAmount();
				chargesUsed.add(charge);
			} else {
				charge.setAmount(charge.getAmount() - chargeNeeded);
				gigaSpace.write(charge);
				Charge chargeUsed = new Charge(charge.getId(), charge.getSupplier(), chargeNeeded);
				chargesUsed.add(chargeUsed);
				createRocket(wood, detonator, Arrays.asList(load), chargesUsed, chargeNeeded);
				return;
			}
			charge = gigaSpace.take(new SQLQuery<Charge>(Charge.class, "amount < 500"));
		}

		charge = gigaSpace.take(new SQLQuery<Charge>(Charge.class, "amount = 500"));
		if (charge == null) {
			log.info("not enough charge");
			throw new RuntimeException();

		}
		charge.setAmount(charge.getAmount() - chargeNeeded);
		gigaSpace.write(charge);
		Charge chargeUsed = new Charge(charge.getId(), charge.getSupplier(), chargeNeeded);
		chargesUsed.add(chargeUsed);

		createRocket(wood, detonator, Arrays.asList(load), chargesUsed, chargeNeeded);
	}

	private void createRocket(Wood wood, Detonator detonator, List<Load> load, List<Charge> chargesUsed, int chargeNeeded) {
		Rocket rocket = new Rocket(wood, detonator, chargesUsed, chargeNeeded, load);

		gigaSpace.write(rocket);
		log.info("created rocket=" + rocket);
	}

	private int calculateCharge() {
		int randomNumber = (int) Math.round(new Random().nextDouble() * 30);
		return 130 + (randomNumber - 15);
	}
}
