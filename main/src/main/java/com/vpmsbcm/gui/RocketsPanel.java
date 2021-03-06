package com.vpmsbcm.gui;

import java.awt.Color;
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

import com.vpmsbcm.gui.models.RocketModel;

@Component
public class RocketsPanel extends JPanel implements ActionListener {

	private JLabel rocketHeadingL;
	private JButton refreshB;
	private JTable rocketT;

	@Autowired
	private RocketModel rocketModel;

	public RocketsPanel() {
	}

	@PostConstruct
	public void init() {
		setBackground(Color.LIGHT_GRAY);
		setOpaque(true);
		setLayout(new GridBagLayout());

		rocketHeadingL = new JLabel("Rockets");
		refreshB = new JButton("refresh");
		refreshB.addActionListener(this);
		rocketT = new JTable(rocketModel);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(rocketHeadingL, constraints);
		constraints.gridx = 1;
		add(refreshB, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(new JScrollPane(rocketT), constraints);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		rocketModel.update();

	}

}
