package com.vpmsbcm.gui.models;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.Charge;
import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.OrderRocket;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.util.Util;

@Component
public class RocketModel extends AbstractTableModel {

	Logger log = LoggerFactory.getLogger(RocketModel.class);

	public List<Rocket> rockets = new LinkedList<Rocket>();

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouseSpace;

	protected String[] columnNames = new String[] { "name", "state", "wood ID", "detonator ID", "load 1", "load 2", "load 3", "charges", "charge amount", "producer ID",
			"controller ID", "exporter ID", "order ID" };

	public RocketModel() {
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
		Rocket rocket = rockets.get(row);
		switch (col) {
		case 0:
			return rocket.getClass().getSimpleName() + " " + Util.splitId(rocket.getId());
		case 1:
			return rocket.getState();
		case 2:
			return rocket.getWood().getId();
		case 3:
			return rocket.getDetonator().getId();
		case 4:
			Load load1 = rocket.getLoades().get(0);
			return load1.getId() + " " + load1.getDefect() + " " + load1.getColor();
		case 5:
			Load load2 = rocket.getLoades().get(1);
			return load2.getId() + " " + load2.getDefect() + " " + load2.getColor();
		case 6:
			Load load3 = rocket.getLoades().get(2);
			return load3.getId() + " " + load3.getDefect() + " " + load3.getColor();
		case 7:
			String text = "";
			for (Charge charge : rocket.getCharges()) {
				text += charge.getId() + "/" + charge.getAmount() + " ";
			}
			return text;
		case 8:
			return rocket.getChargeAmount();
		case 9:
			return rocket.getProducerID() != null ? rocket.getProducerID() : "not set";
		case 10:
			return rocket.getControllerID() != null ? rocket.getControllerID() : "not set";
		case 11:
			return rocket.getExporterID() != null ? rocket.getExporterID() : "not set";
		case 12:
			if (rocket instanceof OrderRocket) {
				return ((OrderRocket) rocket).getOrderId();
			}
			return "";
		default:
			return null;
		}
	}

	public void update() {
		Rocket[] retrievedRockets = warehouseSpace.readMultiple(new Rocket());
		rockets = Arrays.asList(retrievedRockets);
		fireTableDataChanged();
	}
}
