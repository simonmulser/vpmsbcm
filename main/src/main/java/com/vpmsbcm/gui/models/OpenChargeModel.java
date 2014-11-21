package com.vpmsbcm.gui.models;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.Charge;

@Component
public class OpenChargeModel extends AbstractTableModel {

	Logger log = LoggerFactory.getLogger(OpenChargeModel.class);

	public List<Charge> openCharges = new LinkedList<Charge>();

	protected String[] columnNames = new String[] { "charge ID", "amount" };

	public OpenChargeModel() {
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return openCharges.size();
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
			return openCharges.get(row).getId();
		case 1:
			return openCharges.get(row).getAmount();
		default:
			return null;
		}
	}

	public void add(Charge openCharge) {
		int row = 0;
		for (Charge charge : openCharges) {
			if (openCharge.getId().equals(charge.getId())) {
				charge.setAmount(openCharge.getAmount());
				fireTableRowsUpdated(row, row);
				log.debug("updated  charge for table, now " + openCharges.size() + " rockets");
				return;
			}
			row++;
		}
		openCharges.add(openCharge);
		log.debug("received new charge for table, now " + openCharges.size() + " rockets");

		fireTableRowsInserted(row, row);
	}

	public void remove(Charge chargeToRemove) {
		openCharges.remove(chargeToRemove);
		fireTableDataChanged();
	}

}
