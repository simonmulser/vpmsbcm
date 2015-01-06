package com.vpmsbcm.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.order.Order;
import com.vpmsbcm.service.Service;

@Component
public class OrderPanel extends JPanel implements ActionListener {

	final Logger log = LoggerFactory.getLogger(OrderPanel.class);

	@Autowired
	private Service service;

	@GigaSpaceContext
	private GigaSpace warehouseSpace;

	private JButton orderB;
	private JLabel amountL;
	private JTextField amountTF;
	private JLabel unitL;
	private JLabel load1L;
	private JComboBox load1C;
	private JLabel load2L;
	private JComboBox load2C;
	private JLabel load3L;
	private JComboBox load3C;
	private JLabel adressL;
	private JTextField adressF;

	private LinkedList<JComboBox> colorBoxes;

	public OrderPanel() {
	}

	@PostConstruct
	public void init() {
		setBackground(java.awt.Color.LIGHT_GRAY);
		setOpaque(true);

		orderB = new JButton("order");
		orderB.addActionListener(this);

		load1L = new JLabel("load 1");
		load1C = new JComboBox();
		load1C.addItem("red");
		load1C.addItem("green");
		load1C.addItem("blue");

		load2L = new JLabel("load 2");
		load2C = new JComboBox();
		load2C.addItem("red");
		load2C.addItem("green");
		load2C.addItem("blue");

		load3L = new JLabel("load 3");
		load3C = new JComboBox();
		load3C.addItem("red");
		load3C.addItem("green");
		load3C.addItem("blue");

		amountL = new JLabel("amount");
		amountTF = new JTextField(3);
		unitL = new JLabel("rockets ");

		adressL = new JLabel("adress (your adress=" + service.getSpace().getName() + ")");
		adressF = new JTextField(10);

		add(amountL);
		add(amountTF);
		add(unitL);
		add(new JLabel(" | "));
		add(load1L);
		add(load1C);
		add(new JLabel(" | "));
		add(load2L);
		add(load2C);
		add(new JLabel(" | "));
		add(load3L);
		add(load3C);
		add(new JLabel(" | "));
		add(adressL);
		add(adressF);
		add(new JLabel(" | "));
		add(orderB);

		colorBoxes = new LinkedList<JComboBox>();
		colorBoxes.add(load1C);
		colorBoxes.add(load2C);
		colorBoxes.add(load3C);
	}

	public static void main(String[] args) {
		new MainWindow();

	}

	public void actionPerformed(ActionEvent e) {
		int amountRed = 0;
		int amountGreen = 0;
		int amountBlue = 0;

		for (JComboBox comboBox : colorBoxes) {
			if (comboBox.getSelectedItem().equals("red")) {
				amountRed++;
			} else {
				if (comboBox.getSelectedItem().equals("green")) {
					amountGreen++;
				} else {
					amountBlue++;
				}
			}
		}

		Order order = new Order(service.getNextID(), new Integer(amountTF.getText()), amountRed, amountGreen, amountBlue, adressF.getText());

		warehouseSpace.write(order);
		service.getSpace().write(order);
	}
}
