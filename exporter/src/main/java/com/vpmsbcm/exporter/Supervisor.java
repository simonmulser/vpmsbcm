package com.vpmsbcm.exporter;

import javax.annotation.PostConstruct;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.IDFactory;

@Component
public class Supervisor {

	final Logger log = LoggerFactory.getLogger(Supervisor.class);

	private int id;

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouseSpace;

	public Supervisor() {
	}

	@PostConstruct
	public void init() {
		setId();
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

	public int getId() {
		return id;
	}
}
