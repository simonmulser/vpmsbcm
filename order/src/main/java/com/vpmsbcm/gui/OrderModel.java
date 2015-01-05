package com.vpmsbcm.gui;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.Load;
import com.vpmsbcm.common.model.order.Order;
import com.vpmsbcm.common.util.Util;
import com.vpmsbcm.service.Service;

@Component
public class OrderModel extends AbstractTableModel {

	Logger log = LoggerFactory.getLogger(OrderModel.class);

	public List<Order> orders = new LinkedList<Order>();

	@Autowired
	private Service service;

	protected String[] columnNames = new String[] { "name", "amount", "red", "green", "blue", "adress", "state" };

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
		Load load;
		switch (col) {
		case 0:
			return Util.splitId(orders.get(row).getId());
		case 1:
			return orders.get(row).getAmount();
		case 2:
			return orders.get(row).getAmountRed();
		case 3:
			return orders.get(row).getAmountGreen();
		case 4:
			return orders.get(row).getAmountRed();
		case 5:
			return orders.get(row).getAdress();
		case 6:
			return orders.get(row).getState();
		default:
			return null;
		}
	}

	public void update() {
		Order[] retrievedOrders = service.getSpace().readMultiple(new Order());
		orders = Arrays.asList(retrievedOrders);
		fireTableDataChanged();
	}
}
