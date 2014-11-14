package main.java.vpmsbcm.gui;


import java.awt.Color;
import java.awt.GridBagLayout;
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



public class OrderPanel extends JPanel implements ActionListener, ItemListener{
	
	final Logger log = LoggerFactory.getLogger(OrderPanel.class);
	
	final String woodstick = "woodstick";
	final String caseAndDetonator = "case and detonator";
	final String propellingCharge = "propelling charge";
	final String load = "load";
	
	//using prefixes for names
	private JButton orderB;
	private JLabel typeL;
	private JComboBox<String> typeC;
	private JLabel amountL;
	private JTextField amountTF;
	private JLabel unitL;
	private JLabel errorRateL;
	private JTextField errorRateTF;
	private JLabel percentL;


	public OrderPanel() {	
		setBackground(Color.LIGHT_GRAY);
		setOpaque(true);
		
		orderB = new JButton("order");
		orderB.addActionListener(this);
		
		typeL = new JLabel("type");
		
		typeC = new JComboBox<String>();
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
		add(orderB);
	}
	
	public static void main(String[] args) {
		new MainWindow();

	}
	
	private void revert(){
		errorRateTF.setEnabled(false);
		errorRateTF.setText("");
		unitL.setText("units");
	}

	public void itemStateChanged(ItemEvent e) {
	    log.debug(e.toString());		
		if(e.getID() == ItemEvent.ITEM_STATE_CHANGED){
			String text = (String )e.getItem();
			revert();
			if(text.equals(propellingCharge)){
				unitL.setText("500g");
				return;
			}
			if(text.equals(load)){
				errorRateTF.setEnabled(true);
				return;
			}
		}		
	}

	public void actionPerformed(ActionEvent e) {
	    log.debug(e.toString());
	    
	    log.info("order: type=" + typeC.getSelectedItem() + " amount=" + amountTF.getText() + " errorRate=" + errorRateTF.getText());		
	}

}
