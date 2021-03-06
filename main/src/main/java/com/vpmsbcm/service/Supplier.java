package com.vpmsbcm.service;

import java.util.Random;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.Charge;
import com.vpmsbcm.common.model.Color;
import com.vpmsbcm.common.model.Detonator;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.Wood;
import com.vpmsbcm.gui.OrderPanel;

@Component
public class Supplier {

	private static int ID = 1;

	final Logger log = LoggerFactory.getLogger(Supplier.class);

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouseSpace;

	@Autowired
	private Warehouse warehouse;

	public Supplier() {
	}

	public void order(String order, int amount) {
		new Order(order, amount).start();
	}

	public void orderWithRejects(String order, int amount, int errorRate, Color color) {
		new Order(order, amount, errorRate, color).start();
	}

	private class Order extends Thread {

		private String orderable;
		private int amount;
		private double errorRate = 0;
		private Color color;

		public Order(String orderable, int amount, int errorRate, Color color) {
			this(orderable, amount);
			this.errorRate = errorRate / 100.0;
			this.color = color;
		}

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
					object = new Wood("DHL" + ID++);
				} else if (orderable.equals(OrderPanel.caseAndDetonator)) {
					object = new Detonator("DHL" + ID++);
				} else if (orderable.equals(OrderPanel.propellingCharge)) {
					object = new Charge("DHL" + ID++);
				} else if (orderable.equals(OrderPanel.load)) {
					object = new Load("DHL" + ID++, new Boolean(Math.random() <= errorRate), color);
				}

				warehouseSpace.write(object);
				log.info("ordered" + object);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			warehouse.check();
		}
	}
}
