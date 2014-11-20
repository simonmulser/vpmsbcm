package com.vpmsbcm.gui.models;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.Charge;
import com.vpmsbcm.common.model.Detonator;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.Wood;

@Component
public class RocketModel extends AbstractTableModel {

	Logger log = LoggerFactory.getLogger(RocketModel.class);

	public List<Rocket> rockets = new LinkedList<Rocket>();

	protected String[] columnNames = new String[] { "name", "wood ID", "detonator ID", "load ID1", "load ID2", "load ID3", "charges", "charge amount", "producer ID",
			"controller ID", "exporter ID" };

	public RocketModel() {
		Rocket rocket = new Rocket();
		rocket.setId(45);
		rocket.setWood(new Wood("DHL"));
		rocket.setDetonator(new Detonator("DHL"));
		LinkedList<Load> loads = new LinkedList<Load>();
		loads.add(new Load("DHL", true));
		loads.add(new Load("DHL", false));
		loads.add(new Load("DHL", true));
		LinkedList<Charge> charges = new LinkedList<Charge>();
		Charge charge = new Charge(1, "DHL", 45);
		charges.add(charge);
		charges.add(new Charge("DHL"));
		rocket.setCharges(charges);
		rocket.setLoades(loads);
		rocket.setChargeAmount(45);
		rockets.add(rocket);
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return rockets.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Class getColumnClass(int col) {
		return String.class;
	}

	public Object getValueAt(int row, int col) {
		Load load;
		switch (col) {
		case 0:
			return rockets.get(row).getId();
		case 1:
			return rockets.get(row).getWood().getId();
		case 2:
			return rockets.get(row).getDetonator().getId();
		case 3:
			load = rockets.get(row).getLoades().get(0);
			return load.getId() + " " + load.getDefect();
		case 4:
			load = rockets.get(row).getLoades().get(1);
			return load.getId() + " " + load.getDefect();
		case 5:
			load = rockets.get(row).getLoades().get(2);
			return load.getId() + " " + load.getDefect();
		case 6:
			String text = "";
			for (Charge charge : rockets.get(row).getCharges()) {
				text += charge.getId() + "/" + charge.getAmount() + " ";
			}
			return text;
		case 7:
			return rockets.get(row).getChargeAmount();
		case 8:
			return rockets.get(row).getProducerID() != null ? rockets.get(row).getProducerID() : "not set";
		case 9:
			return rockets.get(row).getControllerID() != null ? rockets.get(row).getControllerID() : "not set";
		case 10:
			return rockets.get(row).getExporterID() != null ? rockets.get(row).getExporterID() : "not set";
		default:
			return null;
		}
	}

	public void addRocket(Rocket rocket) {
		int row = rockets.size();
		rockets.add(rocket);
		log.debug("received new rocket for table, now " + rockets.size() + " rockets");

		fireTableRowsInserted(row, row);
	}
}