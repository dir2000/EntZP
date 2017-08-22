package com.zhurylomihaylo.www.entzp;

import javax.swing.table.AbstractTableModel;

class BankTableModel extends AbstractTableModel {

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return Bank.getFieldsCount();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return Entzp.bankList.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
}