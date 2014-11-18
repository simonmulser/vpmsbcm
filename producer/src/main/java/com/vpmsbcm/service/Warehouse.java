package com.vpmsbcm.service;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.CaseWithDetonator;
import com.vpmsbcm.common.model.Good;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.PropellingCharge;
import com.vpmsbcm.common.model.Woodstick;

@Component
public class Warehouse {

	final Logger log = LoggerFactory.getLogger(Warehouse.class);

	private int woodstickCount = 0;
	private int caseWithDetonatorCount = 0;
	private int propellingChargeCount = 0;
	private int loadCount = 0;

	private HashMap<Integer, PropellingCharge> cases;

	public Warehouse() {
	}

	@PostConstruct
	public void init() {
		cases = new HashMap<Integer, PropellingCharge>();
	}

	public synchronized void arrived(Good good) {
		if (good instanceof Woodstick) {
			woodstickCount++;
		} else if (good instanceof CaseWithDetonator) {
			caseWithDetonatorCount++;
		} else if (good instanceof PropellingCharge) {
			propellingChargeCount++;
		} else if (good instanceof Load) {
			loadCount++;
		}
		log.debug("[woodstickCount=" + woodstickCount
				+ ", caseWithDetonatorCount=" + caseWithDetonatorCount
				+ ", propellingChargeCount=" + propellingChargeCount
				+ ", loadCount=" + loadCount + "]");

		if (woodstickCount > 0 && caseWithDetonatorCount > 0
				&& propellingChargeCount > 0 && loadCount > 2)
			log.info("we should produce!");
	}

	@Override
	public String toString() {
		return "Warehouse [woodstickCount=" + woodstickCount
				+ ", caseWithDetonatorCount=" + caseWithDetonatorCount
				+ ", propellingChargeCount=" + propellingChargeCount
				+ ", loadCount=" + loadCount + "]";
	}
}
