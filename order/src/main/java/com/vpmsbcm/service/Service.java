package com.vpmsbcm.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.j_spaces.core.client.SQLQuery;
import com.vpmsbcm.common.model.order.Order;
import com.vpmsbcm.common.model.order.State;

@Component
public class Service {

	final Logger log = LoggerFactory.getLogger(Service.class);

	private String id;
	private AtomicInteger orderId;

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouse;

	private GigaSpace space;

	public Service() {
	}

	public void init(String id) {
		this.id = id;
		createSpace();
		orderId = new AtomicInteger(0);

		getFinishedOrders();

		log.info("started service with id=" + id);
	}

	private void createSpace() {
		space = new GigaSpaceConfigurer(new UrlSpaceConfigurer("/./order-client-" + id)).gigaSpace();
	}

	private void getFinishedOrders() {
		Order[] orders = warehouse.takeMultiple(new SQLQuery<Order>(Order.class, "id LIKE '" + id + ":%' AND state = '" + State.FHINISHED + "'"));
		if (orders != null && orders.length > 0) {
			log.info("got " + orders.length + " fhinished orders");
			for (Order order : orders) {
				order.setState(State.DELIVERED);
			}
			warehouse.writeMultiple(orders);
			space.writeMultiple(orders);
		}
	}

	public GigaSpace getSpace() {
		return space;
	}

	public void setSpace(GigaSpace space) {
		this.space = space;
	}

	public String getNextID() {
		return id + ":" + orderId.incrementAndGet();
	}
}
