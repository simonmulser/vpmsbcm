package com.vpmsbcm.exporter;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.core.space.UrlSpaceConfigurer;
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

import com.vpmsbcm.common.model.OrderRocket;
import com.vpmsbcm.common.model.State;
import com.vpmsbcm.common.model.order.Order;

@TransactionalEvent
@EventDriven
@Polling(gigaSpace = "warehouseSpace")
public class OrderExporter {

	final Logger log = LoggerFactory.getLogger(OrderExporter.class);

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouseSpace;

	@Autowired
	private Supervisor supervisor;

	public OrderExporter() {
	}

	@ReceiveHandler
	ReceiveOperationHandler receiveHandler() {
		SingleTakeReceiveOperationHandler receiveHandler = new SingleTakeReceiveOperationHandler();
		return receiveHandler;
	}

	@EventTemplate
	OrderRocket template() {
		OrderRocket rocket = new OrderRocket();
		rocket.setState(State.CLASS_A);
		return rocket;
	}

	@SpaceDataEvent
	public OrderRocket eventListener(OrderRocket rocket) {
		Order order = new Order();
		order.setId(rocket.getOrderId());
		System.out.println(order);
		order = warehouseSpace.take(order, 200);

		if (order == null) {
			log.error("order can not be found anymore");
		} else {
			log.info("found order=" + order);
			order.getRockets().add(rocket);
			order.decrementMissing();

			if (order.getMissing() <= 0) {
				order.setState(com.vpmsbcm.common.model.order.State.FHINISHED);

				exportOrderedRockets(order);
				log.info("completed order=" + order);
			}

			warehouseSpace.write(order);
		}
		return null;
	}

	private void exportOrderedRockets(Order order) {
		UrlSpaceConfigurer config = new UrlSpaceConfigurer("jini://*/*/testSpace1");
		config.lookupGroups("order-clients");
		GigaSpace gigaSpace = new GigaSpaceConfigurer(config).gigaSpace();
		gigaSpace.write(order);
	}
}
