package com.vpmsbcm.gui.models;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.util.Util;

@Component
public class DestroyedRocketsModel extends AbstractTableModel {

	Logger log = LoggerFactory.getLogger(DestroyedRocketsModel.class);

	private List<Rocket> rockets = new LinkedList<Rocket>();

	protected String[] columnNames = new String[] { "id" };

	public DestroyedRocketsModel() {
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
			return Util.splitId(rockets.get(row).getId());
		default:
			return null;
		}
	}

	public void addRocket(Rocket rocket) {
		rockets.add(rocket);
		fireTableDataChanged();
	}

}
