package com.zhurylomihaylo.www.entzp;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

class BankTableModel extends AbstractTableModel {
	static ArrayList<Bank> bankList = new ArrayList<>();
	static{
		Bank bank = new Bank("Аваль");
		bank.setMonthlyFee(BigDecimal.valueOf(50));
		bank.setTransactionComission(BigDecimal.valueOf(0.0085));
		bank.setTransactionFee(BigDecimal.valueOf(5));
		bankList.add(bank);
		
		Bank bank2 = new Bank("Приватбанк");
		bank2.setMonthlyFee(BigDecimal.valueOf(30));
		bank2.setTransactionComission(BigDecimal.valueOf(0.0085));
		bank2.setTransactionFee(BigDecimal.valueOf(5));
		bankList.add(bank2);		
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return Bank.getFieldsCount();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return BankTableModel.bankList.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Bank bank = bankList.get(arg0);
		return bank.getFieldValue(arg1);
	}
	
}