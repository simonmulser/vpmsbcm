package com.vpmsbcm.exporter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;
import org.openspaces.events.polling.ReceiveHandler;
import org.openspaces.events.polling.receive.MultiTakeReceiveOperationHandler;
import org.openspaces.events.polling.receive.ReceiveOperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j_spaces.core.client.SQLQuery;
import com.vpmsbcm.common.model.IDFactory;
import com.vpmsbcm.common.model.Parcel;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.State;

@EventDriven
@Polling(gigaSpace = "warehouseSpace", passArrayAsIs = true, receiveTimeout = 10000)
public class Exporter {

	final Logger log = LoggerFactory.getLogger(Exporter.class);

	private int id;

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouseSpace;

	public Exporter() {
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

	@ReceiveHandler
	ReceiveOperationHandler receiveHandler() {
		MultiTakeReceiveOperationHandler receiveHandler = new MultiTakeReceiveOperationHandler();
		receiveHandler.setMaxEntries(5);
		receiveHandler.setNonBlocking(true);
		receiveHandler.setNonBlockingFactor(10);
		return receiveHandler;
	}

	@EventTemplate
	SQLQuery<Rocket> template() {
		SQLQuery<Rocket> query = new SQLQuery<Rocket>(Rocket.class, "state = 'WORKING'");
		return query;
	}

	@SpaceDataEvent
	public Rocket[] eventListener(Rocket[] events) {
		log.info("received " + events.length + " rockets");
		List<Rocket> rockets = new LinkedList<Rocket>();
		for (Rocket rocket : events) {
			rocket.setState(State.READY);
			rockets.add(rocket);
		}

		log.info("there are " + rockets.size() + " working rockets");

		int additionalRocketsNeeded = 5 - rockets.size();
		Rocket[] rocketsFromSpace = warehouseSpace.takeMultiple(new SQLQuery<Rocket>(Rocket.class, "state = 'READY'"), additionalRocketsNeeded);
		log.info("got other " + rocketsFromSpace.length + " rockets from the space");

		if (rocketsFromSpace.length + rockets.size() == 5) {
			rockets.addAll(Arrays.asList(rocketsFromSpace));

			for (Rocket rocket : rockets) {
				rocket.setExporterID(id);
			}

			Parcel parcel = new Parcel(rockets);
			warehouseSpace.write(parcel);
			log.info("created parcel=" + parcel);
			return null;
		} else {
			if (rocketsFromSpace.length > 0) {
				warehouseSpace.writeMultiple(rocketsFromSpace);
			}
		}
		return events;
	}

	public Integer getId() {
		return id;
	}
}
