package com.vpmsbcm.gui.models;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.Rocket;

@Component
public class DestroyedRocketsModel extends AbstractTableModel {

	Logger log = LoggerFactory.getLogger(DestroyedRocketsModel.class);

	public List<Rocket> rockets = new LinkedList<Rocket>();

	protected String[] columnNames = new String[] { "id" };

	public DestroyedRocketsModel() {
		Rocket rocket = new Rocket();
		rocket.setId(1);

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
		switch (col) {
		case 0:
			return rockets.get(row).getId();
		default:
			return null;
		}
	}

	public void addRocket(Rocket rocket) {
		// int row = rockets.size();
		// rockets.add(rocket);
		// log.debug("received new rocket for table, now " + rockets.size() +
		// " rockets");
		//
		// fireTableRowsInserted(row, row);
	}

}
