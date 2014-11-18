package com.vpmsbcm.service;

import java.util.HashMap;

import org.openspaces.core.GigaSpace;
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

	public synchronized void newPropellingCharge(
			PropellingCharge propellingCharge) {
		warehousePanel.updatePropellingCharge(1);
		chargeCount += 500;
		charges.put(propellingCharge.getId(), propellingCharge);
		check();
	}

	public synchronized void updateLoad(int i) {
		warehousePanel.updateLoad(i);
		loadCount++;
		check();
	}

	public synchronized void updateCaseAndDetonator(int i) {
		warehousePanel.updateCaseAndDetonator(i);
		casesCount++;
		check();
	}

	public synchronized void updateWoodstick(int i) {
		warehousePanel.updateWoodstick(i);
		woodstickCount++;
		check();
	}

	private void check() {
		int count = gigaSpace.count(new Work());

		int avalaibleWoodsticks = woodstickCount - count;
		int avalaibleCases = casesCount - count;
		int avalaibleCharge = chargeCount - count * 130;
		int avalaibleLoad = loadCount - count * 3;

		if (avalaibleWoodsticks > 0 && avalaibleCases > 0
				&& avalaibleCharge > 130 && avalaibleLoad > 2) {
			gigaSpace.write(new Work());
		}

	}
}
