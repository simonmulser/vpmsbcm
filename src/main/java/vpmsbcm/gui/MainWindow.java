package main.java.vpmsbcm.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class MainWindow extends JFrame {
	
	final Logger log = LoggerFactory.getLogger(MainWindow.class);

	public MainWindow(){		
		setTitle("Feuerwerksfabrik");
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we){
				   System.exit(0);
			}
		});
		getContentPane().add(new OrderPanel());
		pack();
	}
	
	public static void main(String[] args) {
		new MainWindow();

	}
}
