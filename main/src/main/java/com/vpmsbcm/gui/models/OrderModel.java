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

import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.order.Order;
import com.vpmsbcm.common.util.Util;

@Component
public class OrderModel extends AbstractTableModel {

	Logger log = LoggerFactory.getLogger(OrderModel.class);

	public List<Order> orders = new LinkedList<Order>();

	protected String[] columnNames = new String[] { "name", "amount", "missing", "red", "green", "blue", "adress", "state", "rocket IDs" };

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouseSpace;

	public OrderModel() {
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return orders.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Class getColumnClass(int col) {
		return String.class;
	}

	public Object getValueAt(int row, int col) {
		Order order = orders.get(row);
		switch (col) {
		case 0:
			return Util.splitId(order.getId());
		case 1:
			return order.getAmount();
		case 2:
			return order.getMissingRockets();
		case 3:
			return order.getAmountRed();
		case 4:
			return order.getAmountGreen();
		case 5:
			return order.getAmountBlue();
		case 6:
			return order.getAdress();
		case 7:
			return order.getState();
		case 8:
			String ids = "";
			for (Rocket rocket : order.getRockets()) {
				ids += Util.splitId(rocket.getId()) + " ";
			}
			return ids;
		default:
			return null;
		}
	}

	public void update() {
		Order[] retrievedOrders = warehouseSpace.readMultiple(new Order());
		orders = Arrays.asList(retrievedOrders);
		fireTableDataChanged();
	}
}
