package com.zhurylomihaylo.www.entzp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

class MainFrame extends JFrame{
	static final int DEFAULT_WIDTH = 500;
	static final int DEFAULT_HEIGHT = 300;
	JButton addBankButton;
	JButton deleteBankButton;
	JTable bankTable;
	BankTableModel tableModel;
	
	
	MainFrame(){
		buildGUI();
		defineListeners();
	}
	
	void buildGUI(){
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		JPanel banksPane = new JPanel();
		banksPane.setBorder(BorderFactory.createTitledBorder("Тарифи банків"));
		banksPane.setLayout(new BoxLayout(banksPane, BoxLayout.Y_AXIS));
		
		JPanel banksButtonsPane = new JPanel();
		banksButtonsPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		addBankButton = new JButton("Додати");
		banksButtonsPane.add(addBankButton);
		
		deleteBankButton = new JButton("Вилучити");
		banksButtonsPane.add(deleteBankButton);
		
		banksPane.add(banksButtonsPane);
		
		tableModel = new BankTableModel();
		bankTable = new JTable(tableModel);
		for(int i = 0; i < tableModel.getHiddenColumns().length; i++) {
			bankTable.getColumnModel().getColumn(i).setMinWidth(0);	
			bankTable.getColumnModel().getColumn(i).setMaxWidth(0);
		}
		
		banksPane.add(bankTable);
		
		add(banksPane, BorderLayout.NORTH);
	}
	
	void defineListeners(){
		addBankButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.getBankList().add(new Bank());
				tableModel.fireTableDataChanged();
			}
		});
		
		deleteBankButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = bankTable.getSelectedRow();
				if (selectedRow < 0)
					return;
				JOptionPane.showMessageDialog(MainFrame.this, bankTable.getValueAt(selectedRow, Bank.ID_COLUMN));
			}
		});
		
	}
}