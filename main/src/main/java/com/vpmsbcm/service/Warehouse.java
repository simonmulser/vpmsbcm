package com.vpmsbcm.service;

import javax.annotation.PostConstruct;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.Charge;
import com.vpmsbcm.common.model.IDFactory;
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

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouseSpace;

	final Logger log = LoggerFactory.getLogger(Warehouse.class);

	private int woodstickCount = 0;
	private int casesCount = 0;
	private int chargeCount = 0;
	private int loadCount = 0;

	public Warehouse() {
	}

	@PostConstruct
	public void init() {
		IDFactory idFactory = new IDFactory();
		idFactory.init();
		warehouseSpace.write(idFactory);
	}

	public synchronized void addRocket(Rocket event) {
		rocketModel.addRocket(event);
	}

	public synchronized void removeRocket(Rocket event) {
		rocketModel.removeRocket(event);
	}

	public synchronized void newPropellingCharge(Charge charge) {
		if (charge.getAmount() < 500) {
			openChargeModel.add(charge);
		}
		warehousePanel.updateCharge(1);
		chargeCount += charge.getAmount();
	}

	public synchronized void removeCharge(Charge charge) {
		warehousePanel.updateCharge(-1);
		openChargeModel.remove(charge);
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
		int count = warehouseSpace.count(new Work());
		log.debug("count work=" + count);

		int avalaibleWoodsticks = woodstickCount - count;
		int avalaibleCases = casesCount - count;
		int avalaibleCharge = (chargeCount - count * 130) / 130;
		int avalaibleLoad = (loadCount - count * 3) / 3;

		log.info("avalaible: woodsticks=" + avalaibleWoodsticks + " cases=" + avalaibleCases + " charge=" + avalaibleCharge + " load=" + avalaibleLoad);

		int minUnits = getMin(avalaibleWoodsticks, avalaibleCases, avalaibleCharge, avalaibleLoad);

		for (int i = 0; i < minUnits; i++) {
			warehouseSpace.write(new Work());
		}
	}

	private int getMin(int avalaibleWoodsticks, int avalaibleCases, int avalaibleCharge, int avalaibleLoad) {
		int help1 = Math.min(avalaibleCharge, avalaibleCases);
		int help2 = Math.min(avalaibleLoad, avalaibleWoodsticks);
		return Math.min(help1, help2);
	}
}
