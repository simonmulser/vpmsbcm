package com.vpmsbcm.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpmsbcm.service.Supplier;

@Component
public class OrderPanel extends JPanel implements ActionListener, ItemListener {

	@Autowired
	RocketsPanel rocketsPanel;

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
		setBackground(Color.LIGHT_GRAY);
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

		// set initial settings
		revert();
	}

	public static void main(String[] args) {
		new MainWindow();

	}

	private void revert() {
		errorRateTF.setEnabled(false);
		errorRateTF.setText("");
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
			if (errorRateTF.getText().equals("")) {
				supplier.order(typeC.getSelectedItem().toString(), Integer.parseInt(amountTF.getText()));
			} else {
				supplier.orderWithRejects(typeC.getSelectedItem().toString(), Integer.parseInt(amountTF.getText()), Integer.parseInt(errorRateTF.getText()));
			}
		} catch (NumberFormatException exception) {
			log.info("not a number");
		}
	}
}
