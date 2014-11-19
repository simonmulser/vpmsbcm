package com.vpmsbcm.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.annotation.PostConstruct;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpmsbcm.gui.models.DestroyedRocketsModel;
import com.vpmsbcm.gui.models.ExportedRocketsModel;

@Component
public class ExportPanel extends JPanel {

	private JLabel exportedRocketsHeadingL;
	private JLabel destroyedRocketsHeadingL;
	private JTable exportedRocketsT;
	private JTable destroyedRocketsT;

	@Autowired
	private ExportedRocketsModel exportedRocketsModel;

	@Autowired
	private DestroyedRocketsModel destroyedRocketsModel;

	public ExportPanel() {
	}

	@PostConstruct
	public void init() {
		setLayout(new GridBagLayout());

		exportedRocketsHeadingL = new JLabel("Exported rockets");
		destroyedRocketsHeadingL = new JLabel("Destroyed rockets");
		exportedRocketsT = new JTable(exportedRocketsModel);
		destroyedRocketsT = new JTable(destroyedRocketsModel);

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
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(new JScrollPane(exportedRocketsT), constraints);

		constraints.gridx = 1;
		add(new JScrollPane(destroyedRocketsT), constraints);
	}
}
