package com.vpmsbcm.service;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.CaseWithDetonator;
import com.vpmsbcm.common.model.Good;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.PropellingCharge;
import com.vpmsbcm.common.model.Woodstick;
import com.vpmsbcm.gui.WarehousePanel;

@Component
public class Warehouse {

	@Autowired
	private WarehousePanel warehousePanel;

	final Logger log = LoggerFactory.getLogger(Warehouse.class);

	private HashMap<Integer, PropellingCharge> cases;

	public Warehouse() {
	}

	@PostConstruct
	public void init() {
		cases = new HashMap<Integer, PropellingCharge>();
	}

	public synchronized void arrived(Good good) {
		if (good instanceof Woodstick) {
			Woodstick woodstick = (Woodstick) good;
			warehousePanel.updateWoodstick(1);
		} else if (good instanceof CaseWithDetonator) {
			CaseWithDetonator caseWithDetonator = (CaseWithDetonator) good;
			warehousePanel.updateCaseAndDetonator(1);
		} else if (good instanceof PropellingCharge) {
			PropellingCharge propellingCharge = (PropellingCharge) good;
			warehousePanel.updatePropellingCharge(1);
			cases.put(propellingCharge.getId(), propellingCharge);
		} else if (good instanceof Load) {
			Load load = (Load) good;
			warehousePanel.updateLoad(1);
		}
	}
}
