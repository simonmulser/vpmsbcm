package com.vpmsbcm.service;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.IDFactory;

@Component
public class Service {

	final Logger log = LoggerFactory.getLogger(Service.class);

	private int id;
	private AtomicInteger orderId;

	@GigaSpaceContext(name = "warehouse")
	private GigaSpace warehouse;

	private GigaSpace space;

	public Service() {
	}

	@PostConstruct
	public void init() {
		setId();
		createSpace();
		orderId = new AtomicInteger(0);

		log.info("started service with id=" + id);
	}

	private void setId() {
		IDFactory factory = new IDFactory();
		factory.setId(1);

		factory = warehouse.take(factory, 1000);
		System.out.println("!!!!" + warehouse);
		if (factory != null) {
			id = factory.getIdProducer();
			factory.setIdProducer(id + 1);
			warehouse.write(factory);
			log.info("started producer with id=" + id);
		} else {
			id = -1;
		}

	}

	private void createSpace() {
		space = new GigaSpaceConfigurer(new UrlSpaceConfigurer("/./order")).gigaSpace();
	}

	public GigaSpace getSpace() {
		return space;
	}

	public void setSpace(GigaSpace space) {
		this.space = space;
	}

	public String getNextID() {
		return "Order" + id + "Id" + orderId.incrementAndGet();
	}
}
