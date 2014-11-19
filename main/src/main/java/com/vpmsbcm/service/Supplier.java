package com.vpmsbcm.service;

import java.util.Random;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.CaseWithDetonator;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.PropellingCharge;
import com.vpmsbcm.common.model.Woodstick;
import com.vpmsbcm.gui.OrderPanel;

@Component
public class Supplier {

	private static int ID = 1;

	final Logger log = LoggerFactory.getLogger(Supplier.class);

	@GigaSpaceContext
	private GigaSpace gigaSpace;

	@Autowired
	private Warehouse warehouse;

	public Supplier() {
	}

	public void order(String orderable, int amount) {
		new Order(orderable, amount).start();
	}

	private class Order extends Thread {

		private String orderable;
		private int amount;

		public Order(String orderable, int amount) {
			this.orderable = orderable;
			this.amount = amount;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(new Random().nextInt(1) + 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			for (int i = 0; i < amount; i++) {

				Object object = null;
				if (orderable.equals(OrderPanel.woodstick)) {
					object = new Woodstick("DHL" + ID++);
				} else if (orderable.equals(OrderPanel.caseAndDetonator)) {
					object = new CaseWithDetonator("DHL" + ID++);
				} else if (orderable.equals(OrderPanel.propellingCharge)) {
					object = new PropellingCharge("DHL" + ID++);
				} else if (orderable.equals(OrderPanel.load)) {
					object = new Load("DHL" + ID++, new Boolean(Math.random() < 0.5));
				}

				gigaSpace.write(object);
				log.info("ordered" + object);
			}
			warehouse.check();
		}
	}
}
