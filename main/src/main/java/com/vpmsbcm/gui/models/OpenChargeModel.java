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

import com.j_spaces.core.client.SQLQuery;
import com.vpmsbcm.common.model.Charge;

@Component
public class OpenChargeModel extends AbstractTableModel {

	Logger log = LoggerFactory.getLogger(OpenChargeModel.class);

	public List<Charge> openCharges = new LinkedList<Charge>();

	protected String[] columnNames = new String[] { "charge ID", "amount" };

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouseSpace;

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

	public void update() {
		Charge[] retrievedRockets = warehouseSpace.readMultiple(new SQLQuery<Charge>(Charge.class, "amount < 500"));
		openCharges = Arrays.asList(retrievedRockets);
		fireTableDataChanged();
	}

}
