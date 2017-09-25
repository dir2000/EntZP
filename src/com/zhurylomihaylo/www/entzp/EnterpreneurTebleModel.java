package com.zhurylomihaylo.www.entzp;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

class EnterpreneurTableModel extends AbstractTableModel {
	ArrayList<Entrepreneur> entList;
	
	
	EnterpreneurTableModel(ArrayList<Entrepreneur> entList){
		this.entList = entList;
	}	
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return Bank.getFieldsCount();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return entList.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Entrepreneur ent = entList.get(arg0);
		return ent.getFieldValue(arg1);
	}	
}