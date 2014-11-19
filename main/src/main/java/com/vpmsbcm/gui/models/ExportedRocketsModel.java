package com.vpmsbcm.gui.models;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.Parcel;

@Component
public class ExportedRocketsModel extends AbstractTableModel {

	Logger log = LoggerFactory.getLogger(ExportedRocketsModel.class);

	public List<Parcel> parcels = new LinkedList<Parcel>();

	protected String[] columnNames = new String[] { "id", "rocket ID1", "rocket ID2", "rocket ID3", "rocket ID4", "rocket ID5" };

	public ExportedRocketsModel() {
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return parcels.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Class getColumnClass(int col) {
		return String.class;
	}

	public Object getValueAt(int row, int col) {
		// switch (col) {
		// case 0:
		// return rockets.get(row).getId();
		// case 1:
		// return rockets.get(row).getWood().getId();
		// case 2:
		// return rockets.get(row).getDetonator().getId();
		// case 3:
		// return rockets.get(row).getLoades().get(0).getId();
		// case 4:
		// return rockets.get(row).getLoades().get(1).getId();
		// case 5:
		// return rockets.get(row).getLoades().get(2).getId();
		// case 6:
		// String text = "";
		// for (Charge charge : rockets.get(row).getCharges()) {
		// text += charge.getId() + "/" + charge.getAmount() + " ";
		// }
		// return text;
		// default:
		// return null;
		// }
		return null;
	}

	public void addParcel(Parcel parcel) {
		// int row = rockets.size();
		// rockets.add(rocket);
		// log.debug("received new rocket for table, now " + rockets.size() +
		// " rockets");
		//
		// fireTableRowsInserted(row, row);
	}

}
