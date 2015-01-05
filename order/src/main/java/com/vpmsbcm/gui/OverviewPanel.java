package com.vpmsbcm.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OverviewPanel extends JPanel implements ActionListener {

	private JLabel orderL;
	private JButton refreshB;
	private JTable orderT;

	@Autowired
	private OrderModel orderModel;

	public OverviewPanel() {
	}

	@PostConstruct
	public void init() {
		setOpaque(true);
		setLayout(new GridBagLayout());

		orderL = new JLabel("Orders");
		refreshB = new JButton("refresh");
		refreshB.addActionListener(this);
		orderT = new JTable(orderModel);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(orderL, constraints);
		constraints.gridx = 1;
		add(refreshB, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(new JScrollPane(orderT), constraints);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		orderModel.update();
	}
}
