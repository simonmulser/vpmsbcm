package com.vpmsbcm.service;

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

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouseSpace;
	private GigaSpace space;

	public Service() {
	}

	@PostConstruct
	public void init() {
		setId();

		createSpace();
	}

	private void setId() {
		IDFactory factory = new IDFactory();
		factory.setId(1);

		factory = warehouseSpace.take(factory, 1000);
		if (factory != null) {
			id = factory.getIdExporter();
			factory.setIdExporter(id + 1);
			warehouseSpace.write(factory);
			log.info("started exporter with id=" + id);
		} else {
			id = -1;
		}
	}

	private void createSpace() {
		space = new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/order" + id)).gigaSpace();
	}

}
