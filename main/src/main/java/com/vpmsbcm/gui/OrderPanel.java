package com.vpmsbcm.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

import com.vpmsbcm.common.model.Color;
import com.vpmsbcm.service.Supplier;

@Component
public class OrderPanel extends JPanel implements ActionListener, ItemListener {

	@Autowired
	RocketsPanel rocketsPanel;

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouseSpace;

	final Logger log = LoggerFactory.getLogger(OrderPanel.class);

	public final static String woodstick = "woodstick";
	public final static String caseAndDetonator = "case and detonator";
	public final static String propellingCharge = "propelling charge";
	public final static String load = "load";

	private JButton orderB;
	private JLabel typeL;
	private JComboBox typeC;
	private JLabel amountL;
	private JTextField amountTF;
	private JLabel unitL;
	private JLabel errorRateL;
	private JTextField errorRateTF;
	private JLabel percentL;
	private JLabel colorL;
	private JComboBox colorC;

	@Autowired
	private Supplier supplier;

	public OrderPanel() {
	}

	@PostConstruct
	public void init() {
		setBackground(java.awt.Color.LIGHT_GRAY);
		setOpaque(true);

		orderB = new JButton("order");
		orderB.addActionListener(this);

		typeL = new JLabel("type");

		typeC = new JComboBox();
		typeC.addItem(woodstick);
		typeC.addItem(caseAndDetonator);
		typeC.addItem(propellingCharge);
		typeC.addItem(load);
		typeC.addItemListener(this);

		amountL = new JLabel("amount");
		amountTF = new JTextField(3);
		unitL = new JLabel("units ");

		errorRateL = new JLabel("error rate");
		errorRateTF = new JTextField(3);
		errorRateTF.setEnabled(false);
		percentL = new JLabel("%");

		colorL = new JLabel("color");

		colorC = new JComboBox();
		colorC.addItem("red");
		colorC.addItem("green");
		colorC.addItem("blue");

		add(typeL);
		add(typeC);
		add(new JLabel(" | "));
		add(amountL);
		add(amountTF);
		add(unitL);
		add(new JLabel(" | "));
		add(errorRateL);
		add(errorRateTF);
		add(percentL);
		add(new JLabel(" | "));
		add(colorL);
		add(colorC);
		add(new JLabel(" | "));
		add(orderB);

		// Benchmarkbutton
		add(new JLabel(" | "));
		JButton button = new JButton("Start Benchmark");
		button.addActionListener(new BenchmarkStarter(warehouseSpace));
		add(button);

		// set initial settings
		revert();
	}

	private void revert() {
		errorRateTF.setEnabled(false);
		errorRateTF.setText("0");
		unitL.setText("units");

		colorC.setEnabled(false);
	}

	public void itemStateChanged(ItemEvent e) {
		log.debug(e.toString());
		if (e.getID() == ItemEvent.ITEM_STATE_CHANGED) {
			String text = (String) e.getItem();
			revert();
			if (text.equals(propellingCharge)) {
				unitL.setText("500g");
				return;
			}
			if (text.equals(load)) {
				errorRateTF.setEnabled(true);
				colorC.setEnabled(true);
				return;
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (typeC.getSelectedItem().toString().equals("load")) {
				supplier.orderWithRejects(typeC.getSelectedItem().toString(), Integer.parseInt(amountTF.getText()), Integer.parseInt(errorRateTF.getText()),
						Color.valueOf(colorC.getSelectedItem().toString().toUpperCase()));
			} else {
				supplier.order(typeC.getSelectedItem().toString(), Integer.parseInt(amountTF.getText()));
			}
		} catch (NumberFormatException exception) {
			log.info("not a number");
		}
	}
}
