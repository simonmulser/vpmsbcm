package com.vpmsbcm.gui.models;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.Parcel;
import com.vpmsbcm.common.util.Util;

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
		switch (col) {
		case 0:
			return Util.splitId(parcels.get(row).getId());
		case 1:
			return Util.splitId(parcels.get(row).getRockets().get(0).getId());
		case 2:
			return Util.splitId(parcels.get(row).getRockets().get(1).getId());
		case 3:
			return Util.splitId(parcels.get(row).getRockets().get(2).getId());
		case 4:
			return Util.splitId(parcels.get(row).getRockets().get(3).getId());
		case 5:
			return Util.splitId(parcels.get(row).getRockets().get(4).getId());
		default:
			return null;
		}
	}

	public void addParcel(Parcel parcel) {
		parcels.add(parcel);
		fireTableDataChanged();
	}

}
