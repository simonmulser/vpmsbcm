package com.vpmsbcm.producer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ArrayUtils;
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
import com.vpmsbcm.common.model.IDFactory;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.NormalRocket;
import com.vpmsbcm.common.model.OrderRocket;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.Wood;
import com.vpmsbcm.common.model.Work;
import com.vpmsbcm.common.model.order.Order;

@EventDriven
@Polling
public class Producer {

	final Logger log = LoggerFactory.getLogger(Producer.class);

	private int id;

	private int amountRed;
	private int amountGreen;
	private int amountBlue;

	@GigaSpaceContext
	private GigaSpace warehouse;

	public Producer() {
	}

	@PostConstruct
	public void init() {
		setId();
	}

	private void setId() {
		IDFactory factory = new IDFactory();
		factory.setId(1);

		factory = warehouse.take(factory, 1000);
		if (factory != null) {
			id = factory.getIdProducer();
			factory.setIdProducer(id + 1);
			warehouse.write(factory);
			log.info("started producer with id=" + id);
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
	Work template() {
		Work template = new Work();
		return template;
	}

	@SpaceDataEvent
	@Transactional
	public Work eventListener(Work event) {
		log.info("received work=" + event);
		produce();

		// don't write work back into the space
		return null;
	}

	private void produce() {
		updateColors();
		log.info("updated colors: red=" + amountRed + " green=" + amountGreen + " blue=" + amountBlue);

		Order order = getSuitableOrder();
		log.info("suiteabel order=" + order);

		Load[] load = new Load[0];
		if (order != null) {
			load = ArrayUtils.addAll(load, warehouse.takeMultiple(new SQLQuery<Load>(Load.class, "color = 'RED'"), order.getAmountRed()));
			load = ArrayUtils.addAll(load, warehouse.takeMultiple(new SQLQuery<Load>(Load.class, "color = 'GREEN'"), order.getAmountGreen()));
			load = ArrayUtils.addAll(load, warehouse.takeMultiple(new SQLQuery<Load>(Load.class, "color = 'BLUE'"), order.getAmountBlue()));
		} else {
			load = warehouse.takeMultiple(new Load(), 3);
		}
		if (load.length != 3) {
			throw new NotEnoughGoodsException("not enough loads! only " + load.length + " loads, 3 needed");

		}

		int chargeNeeded = calculateCharge();
		log.debug("chargeNeeded=" + chargeNeeded);

		Wood wood = warehouse.take(new Wood());
		if (wood == null) {
			throw new NotEnoughGoodsException("not enough woodsticks!");
		}

		Detonator detonator = warehouse.take(new Detonator());
		if (detonator == null) {
			throw new NotEnoughGoodsException("not enough detonator!");

		}

		LinkedList<Charge> chargesUsed = new LinkedList<Charge>();
		int chargeLeft = chargeNeeded;
		Charge charge = warehouse.take(new SQLQuery<Charge>(Charge.class, "amount < 500"));
		while (charge != null) {
			if (charge.getAmount() <= chargeLeft) {
				chargeLeft = chargeLeft - charge.getAmount();
				chargesUsed.add(charge);
			} else {
				charge.setAmount(charge.getAmount() - chargeLeft);
				warehouse.write(charge);
				Charge chargeUsed = new Charge(charge.getId(), charge.getSupplier(), chargeLeft);
				chargesUsed.add(chargeUsed);
				createRocket(wood, detonator, Arrays.asList(load), chargesUsed, chargeNeeded, order);
				return;
			}
			charge = warehouse.take(new SQLQuery<Charge>(Charge.class, "amount < 500"));
		}

		charge = warehouse.take(new SQLQuery<Charge>(Charge.class, "amount = 500"));
		if (charge == null) {
			throw new NotEnoughGoodsException("not enough charge");

		}
		charge.setAmount(charge.getAmount() - chargeLeft);
		warehouse.write(charge);
		Charge chargeUsed = new Charge(charge.getId(), charge.getSupplier(), chargeLeft);
		chargesUsed.add(chargeUsed);

		createRocket(wood, detonator, Arrays.asList(load), chargesUsed, chargeNeeded, order);
	}

	private Order getSuitableOrder() {
		return warehouse.take(new SQLQuery<Order>(Order.class, "amountRed <= " + amountRed + "AND amountGreen <= " + amountGreen + "AND amountBlue <= " + amountBlue));
	}

	private void updateColors() {
		amountRed = warehouse.count(new SQLQuery<Load>(Load.class, "color = 'RED'"));
		amountGreen = warehouse.count(new SQLQuery<Load>(Load.class, "color = 'GREEN'"));
		amountBlue = warehouse.count(new SQLQuery<Load>(Load.class, "color = 'BLUE'"));
	}

	private void createRocket(Wood wood, Detonator detonator, List<Load> load, List<Charge> chargesUsed, int chargeNeeded, Order order) {
		Rocket rocket = null;
		if (order == null) {
			rocket = new NormalRocket(wood, detonator, chargesUsed, chargeNeeded, load, id);
		} else {
			rocket = new OrderRocket(wood, detonator, chargesUsed, chargeNeeded, load, id, order.getId());
		}
		warehouse.write(rocket);
		log.info("created rocket=" + rocket);
	}

	private int calculateCharge() {
		int randomNumber = (int) Math.round(new Random().nextDouble() * 30);
		return 130 + (randomNumber - 15);
	}
}
