package com.vpmsbcm.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

@Component
public class ExportPanel extends JPanel {

	private JLabel exportedRocketsHeadingL;
	private JLabel destroyedRocketsHeadingL;
	private JLabel exportedRocketsL;
	private JLabel destroyedRocketsL;

	public ExportPanel() {
		setLayout(new GridBagLayout());

		exportedRocketsHeadingL = new JLabel("Exported rockets");
		destroyedRocketsHeadingL = new JLabel("Destroyed rockets");
		exportedRocketsL = new JLabel("no exported rockets");
		destroyedRocketsL = new JLabel("no destroyed rockets");

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		add(exportedRocketsHeadingL, constraints);

		constraints.gridx = 1;
		add(destroyedRocketsHeadingL, constraints);

		constraints.weighty = 1;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.PAGE_START;
		add(exportedRocketsL, constraints);

		constraints.gridx = 1;
		add(destroyedRocketsL, constraints);
	}
}
