package com.vpmsbcm.service;

import java.util.HashMap;

import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.PropellingCharge;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.Work;
import com.vpmsbcm.gui.RocketsPanel;
import com.vpmsbcm.gui.WarehousePanel;

@Component
public class Warehouse {

	@Autowired
	private WarehousePanel warehousePanel;

	@Autowired
	private RocketsPanel rocketsPanel;

	@Autowired
	private GigaSpace gigaSpace;

	final Logger log = LoggerFactory.getLogger(Warehouse.class);

	private int woodstickCount = 0;
	private int casesCount = 0;
	private int chargeCount = 0;
	private int loadCount = 0;

	private HashMap<Integer, PropellingCharge> charges = new HashMap<Integer, PropellingCharge>();

	public Warehouse() {
	}

	public synchronized void addRocket(Rocket event) {
		rocketsPanel.addRocket();
	}

	public synchronized void newPropellingCharge(PropellingCharge charge) {
		PropellingCharge chargeOld = charges.get(charge.getId());
		if (chargeOld != null) {
			chargeCount = chargeCount - chargeOld.getAmount() + charge.getAmount();
		} else {
			warehousePanel.updatePropellingCharge(1);
			chargeCount += 500;
		}
		charges.put(charge.getId(), charge);

	}

	public synchronized void removePropellingCharge(PropellingCharge charge) {
		chargeCount -= charge.getAmount();
	}

	public synchronized void updateLoad(int i) {
		warehousePanel.updateLoad(i);
		loadCount += i;
	}

	public synchronized void updateCaseAndDetonator(int i) {
		warehousePanel.updateCaseAndDetonator(i);
		casesCount += i;
	}

	public synchronized void updateWoodstick(int i) {
		warehousePanel.updateWoodstick(i);
		woodstickCount += i;
	}

	public void check() {
		int count = gigaSpace.readMultiple(new Work()).length;
		log.debug("count work=" + count);

		int avalaibleWoodsticks = woodstickCount - count;
		int avalaibleCases = casesCount - count;
		int avalaibleCharge = chargeCount - count * 130;
		int avalaibleLoad = loadCount - count * 3;

		log.info("avalaible: woodsticks=" + avalaibleWoodsticks + " cases=" + avalaibleCases + " charge=" + avalaibleCharge + " load=" + avalaibleLoad);

		if (avalaibleWoodsticks > 0 && avalaibleCases > 0 && avalaibleCharge > 130 && avalaibleLoad > 2) {
			gigaSpace.write(new Work());
			log.info("created work");
		}
	}
}
