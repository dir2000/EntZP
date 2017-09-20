package com.zhurylomihaylo.www.entzp;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

class BankTableModel extends AbstractTableModel {
	ArrayList<Bank> bankList;
	private int [] hiddenColumns = new int [] {0}; 
	
	BankTableModel(ArrayList<Bank> bankList){
		this.bankList = bankList;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return Bank.getFieldsCount();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return bankList.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Bank bank = bankList.get(arg0);
		return bank.getFieldValue(arg1);
	}
	
	@Override	
	public void setValueAt(Object value, int r, int c) {
		Bank bank = DataStorage.getBankList().get(r);
		bank.setFieldValue(c, value);
		//JOptionPane.showMessageDialog(null, bank);
	}
	
	public boolean isCellEditable(int r, int ñ){
		return true;
	}	
	
	int [] getHiddenColumns(){
		return hiddenColumns;
	}
}