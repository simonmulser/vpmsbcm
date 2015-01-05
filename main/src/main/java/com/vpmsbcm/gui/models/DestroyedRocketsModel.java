package com.vpmsbcm.gui.models;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.NormalRocket;
import com.vpmsbcm.common.util.Util;

@Component
public class DestroyedRocketsModel extends AbstractTableModel {

	Logger log = LoggerFactory.getLogger(DestroyedRocketsModel.class);

	private List<NormalRocket> rockets = new LinkedList<NormalRocket>();

	protected String[] columnNames = new String[] { "id", "state", "producer ID", "controller ID", "exporter ID" };

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
		case 1:
			return rockets.get(row).getState();
		case 2:
			return rockets.get(row).getProducerID() != null ? rockets.get(row).getProducerID() : "not set";
		case 3:
			return rockets.get(row).getControllerID() != null ? rockets.get(row).getControllerID() : "not set";
		case 4:
			return rockets.get(row).getExporterID() != null ? rockets.get(row).getExporterID() : "not set";

		default:
			return null;
		}
	}

	public void addRocket(NormalRocket rocket) {
		rockets.add(rocket);
		fireTableDataChanged();
	}

}
