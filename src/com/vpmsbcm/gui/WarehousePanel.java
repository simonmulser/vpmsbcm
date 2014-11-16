package com.vpmsbcm.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WarehousePanel extends JPanel{
	
	private JLabel warehouseHeadingL;
	private JLabel openPropellingChargeHeadingL;
	private JPanel depotP;
	private JLabel woodstickL;
	private JLabel caseAndDetonatorL;
	private JLabel propellingChargeL;
	private JLabel loadL;
	private JLabel openPropellingChargeL;

	
	public WarehousePanel(){
		setLayout(new GridBagLayout());
		
		warehouseHeadingL = new JLabel("Warehouse");
		openPropellingChargeHeadingL = new JLabel("Open propelling charge");
		
		woodstickL = new JLabel("woodsticks: 0");
		caseAndDetonatorL = new JLabel("case and detonator: 0");
		propellingChargeL = new JLabel("propelling charge: 0");
		loadL = new JLabel("load: 0");
		depotP = new JPanel(new GridLayout(0,1));
		depotP.add(woodstickL);
		depotP.add(caseAndDetonatorL);
		depotP.add(propellingChargeL);
		depotP.add(loadL);
		
		openPropellingChargeL = new JLabel("no open propelling charge");
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		add(warehouseHeadingL, constraints);
		
		constraints.gridx = 1;
		add(openPropellingChargeHeadingL, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.PAGE_START;
		add(depotP, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		add(openPropellingChargeL, constraints);
	}

}
