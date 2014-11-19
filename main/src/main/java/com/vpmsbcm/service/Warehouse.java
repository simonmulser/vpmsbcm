package com.vpmsbcm.service;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.Charge;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.Work;
import com.vpmsbcm.gui.WarehousePanel;
import com.vpmsbcm.gui.models.OpenChargeModel;
import com.vpmsbcm.gui.models.RocketModel;

@Component
public class Warehouse {

	@Autowired
	private WarehousePanel warehousePanel;

	@Autowired
	private RocketModel rocketModel;

	@Autowired
	private OpenChargeModel openChargeModel;

	@Autowired
	private GigaSpace gigaSpace;

	final Logger log = LoggerFactory.getLogger(Warehouse.class);

	private int woodstickCount = 0;
	private int casesCount = 0;
	private int chargeCount = 0;
	private int loadCount = 0;

	private HashMap<Integer, Charge> charges = new HashMap<Integer, Charge>();

	public Warehouse() {
	}

	@PostConstruct
	public void init() {
	}

	public synchronized void addRocket(Rocket event) {
		rocketModel.addRocket(event);
	}

	public synchronized void newPropellingCharge(Charge charge) {
		Charge chargeOld = charges.get(charge.getId());
		if (chargeOld != null) {
			chargeCount = chargeCount - chargeOld.getAmount() + charge.getAmount();
			openChargeModel.add(charge);
		} else {
			warehousePanel.updatePropellingCharge(1);
			chargeCount += 500;
		}
		charges.put(charge.getId(), charge);

	}

	public synchronized void removePropellingCharge(Charge charge) {
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

	public synchronized void check() {
		int count = gigaSpace.count(new Work());
		log.debug("count work=" + count);

		int avalaibleWoodsticks = woodstickCount - count;
		int avalaibleCases = casesCount - count;
		int avalaibleCharge = (chargeCount - count * 130) / 130;
		int avalaibleLoad = (loadCount - count * 3) / 3;

		log.info("avalaible: woodsticks=" + avalaibleWoodsticks + " cases=" + avalaibleCases + " charge=" + avalaibleCharge + " load=" + avalaibleLoad);

		int minUnits = getMin(avalaibleWoodsticks, avalaibleCases, avalaibleCharge, avalaibleLoad);

		for (int i = 0; i < minUnits; i++) {
			gigaSpace.write(new Work());
		}
	}

	private int getMin(int avalaibleWoodsticks, int avalaibleCases, int avalaibleCharge, int avalaibleLoad) {
		int help1 = Math.min(avalaibleCharge, avalaibleCases);
		int help2 = Math.min(avalaibleLoad, avalaibleWoodsticks);
		return Math.min(help1, help2);
	}
}
