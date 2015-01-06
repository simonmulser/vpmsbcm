package com.vpmsbcm.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.openspaces.core.GigaSpace;

import com.vpmsbcm.common.model.Work;

public class BenchmarkStarter implements ActionListener {

	private GigaSpace warehouseSpace;
	private int amount = 500;

	public BenchmarkStarter(GigaSpace warehouseSpace) {
		this.warehouseSpace = warehouseSpace;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Work[] work = new Work[amount];
		for (int i = 0; i < amount; i++) {
			work[i] = new Work();
		}

		warehouseSpace.writeMultiple(work);
	}
}
