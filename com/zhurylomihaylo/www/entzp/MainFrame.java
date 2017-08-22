package com.zhurylomihaylo.www.entzp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

class MainFrame extends JFrame{
	static final int DEFAULT_WIDTH = 500;
	static final int DEFAULT_HEIGHT = 300;
	
	MainFrame(){
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		JPanel banksPane = new JPanel();
		banksPane.setBorder(BorderFactory.createTitledBorder("Тарифи банків"));
		banksPane.setLayout(new BoxLayout(banksPane, BoxLayout.Y_AXIS));
		
		JPanel banksButtonsPane = new JPanel();
		banksButtonsPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton addBankButton = new JButton("Додати");
		banksButtonsPane.add(addBankButton);
		JButton deleteBankButton = new JButton("Вилучити");
		banksButtonsPane.add(deleteBankButton);
		banksPane.add(banksButtonsPane);
		
		JTable bankTable = new JTable(new BankTableModel());
		banksPane.add(bankTable);
		add(banksPane, BorderLayout.NORTH);
	}
}