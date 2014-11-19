package com.vpmsbcm.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import com.vpmsbcm.common.model.PropellingCharge;

@Component
public class WarehousePanel extends JPanel {

	private JLabel warehouseHeadingL;
	private JLabel openPropellingChargeHeadingL;
	private JPanel depotP;
	private JLabel woodstickL;
	private JLabel caseAndDetonatorL;
	private JLabel propellingChargeL;
	private JLabel loadL;
	private JLabel numberWoodstickL;
	private JLabel numberCaseAndDetonatorL;
	private JLabel numberPropellingChargeL;
	private JLabel numberLoadL;
	private JLabel openPropellingChargeL;

	public WarehousePanel() {
	}

	@PostConstruct
	public void init() {
		setLayout(new GridBagLayout());

		warehouseHeadingL = new JLabel("Warehouse");
		openPropellingChargeHeadingL = new JLabel("Open propelling charge");

		woodstickL = new JLabel("woodsticks:");
		caseAndDetonatorL = new JLabel("case and detonator:");
		propellingChargeL = new JLabel("propelling charge:");
		loadL = new JLabel("load:");
		numberWoodstickL = new JLabel("0");
		numberCaseAndDetonatorL = new JLabel("0");
		numberPropellingChargeL = new JLabel("0");
		numberLoadL = new JLabel("0");
		depotP = new JPanel(new GridLayout(0, 2));
		depotP.add(woodstickL);
		depotP.add(numberWoodstickL);
		depotP.add(caseAndDetonatorL);
		depotP.add(numberCaseAndDetonatorL);
		depotP.add(propellingChargeL);
		depotP.add(numberPropellingChargeL);
		depotP.add(loadL);
		depotP.add(numberLoadL);

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

	public void updateWoodstick(int amount) {
		numberWoodstickL.setText(Integer.parseInt(numberWoodstickL.getText()) + amount + "");
	}

	public void updateCaseAndDetonator(int amount) {
		numberCaseAndDetonatorL.setText(Integer.parseInt(numberCaseAndDetonatorL.getText()) + amount + "");
	}

	public void updatePropellingCharge(int amount) {
		numberPropellingChargeL.setText(Integer.parseInt(numberPropellingChargeL.getText()) + amount + "");
	}

	public void updateLoad(int amount) {
		numberLoadL.setText(Integer.parseInt(numberLoadL.getText()) + amount + "");
	}

	public void updateOpenCharges(HashMap<Integer, PropellingCharge> charges) {
		openPropellingChargeL.setText("");
		String text = "";
		for (Integer key : charges.keySet()) {
			PropellingCharge charge = charges.get(key);
			if (charge.getAmount() < 500)
				text += charges.get(key) + "\n";
		}
		openPropellingChargeL.setText(text);
	}
}
