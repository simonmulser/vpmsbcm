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
import com.vpmsbcm.common.model.Parcel;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.gui.WarehousePanel;
import com.vpmsbcm.gui.models.DestroyedRocketsModel;
import com.vpmsbcm.gui.models.ExportedRocketsModel;
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
	private ExportedRocketsModel exportedRocketsModel;

	@Autowired
	private DestroyedRocketsModel destroyedRocketsModel;

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

	public synchronized void newPropellingCharge(Charge charge) {
		warehousePanel.updateCharge(1);
		chargeCount += charge.getAmount();
	}

	public synchronized void removeCharge(Charge charge) {
		warehousePanel.updateCharge(-1);
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

	public synchronized void addParcel(Parcel parcel) {
		exportedRocketsModel.addParcel(parcel);
	}

	public synchronized void addDestroyedRocket(Rocket rocket) {
		destroyedRocketsModel.addRocket(rocket);
	}

	public synchronized void check() {
		// for benchmark comment this out

		// int count = warehouseSpace.count(new Work());
		// log.debug("already " + count +
		// " work element(s) available in the space");
		//
		// int avalaibleWoodsticks = woodstickCount - count;
		// int avalaibleCases = casesCount - count;
		// int avalaibleCharge = (chargeCount - count * 130) / 130;
		// int avalaibleLoad = (loadCount - count * 3) / 3;
		//
		// log.info("avalaible: woodsticks=" + avalaibleWoodsticks + " cases=" +
		// avalaibleCases + " charge=" + avalaibleCharge + " load=" +
		// avalaibleLoad);
		//
		// int minUnits = getMin(avalaibleWoodsticks, avalaibleCases,
		// avalaibleCharge, avalaibleLoad);
		//
		// log.info("writing " + minUnits +
		// " new work element(s) into the space");
		// for (int i = 0; i < minUnits; i++) {
		// warehouseSpace.write(new Work());
		// }
	}

	private int getMin(int avalaibleWoodsticks, int avalaibleCases, int avalaibleCharge, int avalaibleLoad) {
		int help1 = Math.min(avalaibleCharge, avalaibleCases);
		int help2 = Math.min(avalaibleLoad, avalaibleWoodsticks);
		return Math.min(help1, help2);
	}
}
