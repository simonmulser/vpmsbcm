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

@Component
public class MainWindow extends JFrame {

	final Logger log = LoggerFactory.getLogger(MainWindow.class);

	private JPanel contentPanel;

	@Autowired
	private OrderPanel orderPanel;

	public MainWindow() {
	}

	@PostConstruct
	public void init() {
		setTitle("Order");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.BOTH;
		contentPanel.add(orderPanel, constraints);

		setContentPane(contentPanel);
		pack();

		setMinimumSize(new Dimension(getWidth(), getHeight()));
	}

	public static void main(String[] args) {
		new MainWindow();

	}
}
