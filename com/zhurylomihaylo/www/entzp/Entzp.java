package com.zhurylomihaylo.www.entzp;

import javax.swing.*;

import java.awt.EventQueue;

public class Entzp {
	static private DataStorage dataStorage;
	
	static{
		dataStorage = new DataStorage();		 
	 }
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame frame = new MainFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("Розрахунок оплати приватним підприємцям");
				frame.setVisible(true);
			}
		});
	}
	
	static DataStorage getDataStorage() {
		return dataStorage;
	}
}
