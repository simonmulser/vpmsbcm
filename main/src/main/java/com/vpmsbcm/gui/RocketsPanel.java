package com.vpmsbcm.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class RocketsPanel extends JPanel {
	
	private JLabel rocketHeadingL;
	private JLabel rocketL;

	
	public RocketsPanel(){
		setBackground(Color.LIGHT_GRAY);
		setOpaque(true);
		setLayout(new GridBagLayout());
		
		rocketHeadingL = new JLabel("Rockets");
		rocketL = new JLabel("no rockets");
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(rocketHeadingL, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.PAGE_START;
		add(rocketL, constraints);
	}

}
