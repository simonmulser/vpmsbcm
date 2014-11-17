package com.vpmsbcm.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpmsbcm.service.Supplier;

@Component
public class MainWindow extends JFrame {

	final Logger log = LoggerFactory.getLogger(MainWindow.class);

	private JPanel contentPanel;

	@Autowired
	private Supplier supplier;

	@Autowired
	private WarehousePanel warehousePanel;

	public MainWindow() {
	}

	@PostConstruct
	public void init() {
		System.out.println(supplier);
		setTitle("Feuerwerksfabrik");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.BOTH;
		contentPanel.add(new OrderPanel(supplier), constraints);

		constraints.gridy = 1;
		constraints.weighty = 0.3;
		contentPanel.add(warehousePanel, constraints);

		constraints.gridy = 2;
		constraints.weighty = 0.3;
		contentPanel.add(new RocketsPanel(), constraints);

		constraints.gridy = 3;
		constraints.weighty = 0.3;
		contentPanel.add(new ExportPanel(), constraints);

		setContentPane(contentPanel);
		pack();

		setMinimumSize(new Dimension(getWidth(), getHeight()));
	}

	public static void main(String[] args) {
		new MainWindow();

	}
}
