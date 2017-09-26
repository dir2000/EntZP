package com.zhurylomihaylo.www.entzp;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

class EntTableModel extends AbstractTableModel {
	ArrayList<Ent> entList;
	
	
	EntTableModel(ArrayList<Ent> entList){
		this.entList = entList;
	}	
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return Ent.getFieldsCount();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return entList.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Ent ent = entList.get(arg0);
		return ent.getFieldValue(arg1);
	}	
}