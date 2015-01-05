package com.vpmsbcm.exporter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.TransactionalEvent;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.ReceiveHandler;
import org.openspaces.events.polling.receive.MultiTakeReceiveOperationHandler;
import org.openspaces.events.polling.receive.ReceiveOperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.j_spaces.core.client.SQLQuery;
import com.vpmsbcm.common.model.Parcel;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.State;

@TransactionalEvent
public class Exporter {

	final Logger log = LoggerFactory.getLogger(Exporter.class);

	private String type;

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouseSpace;

	@Autowired
	private Supervisor supervisor;

	public Exporter() {
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
		SQLQuery<Rocket> query = new SQLQuery<Rocket>(Rocket.class, "state = '" + type + "'");
		return query;
	}

	@SpaceDataEvent
	public Rocket[] eventListener(Rocket[] events) {
		log.info("received " + events.length + " rockets from type " + type);
		List<Rocket> rockets = new LinkedList<Rocket>();
		for (Rocket rocket : events) {
			rocket.setState(State.valueOf("READY_" + type));
			rockets.add(rocket);
		}

		log.info("there are " + rockets.size() + " working rockets");

		int additionalRocketsNeeded = 5 - rockets.size();
		Rocket[] rocketsFromSpace = warehouseSpace.takeMultiple(new SQLQuery<Rocket>(Rocket.class, "state = 'READY_" + type + "'"), additionalRocketsNeeded);
		log.info("got other " + rocketsFromSpace.length + " rockets from the space");

		if (rocketsFromSpace.length + rockets.size() == 5) {
			rockets.addAll(Arrays.asList(rocketsFromSpace));

			for (Rocket rocket : rockets) {
				rocket.setExporterID(supervisor.getId());
				rocket.setState(State.valueOf(type));
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
