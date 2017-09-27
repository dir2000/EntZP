package com.zhurylomihaylo.www.entzp;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

class EntTableModel extends AbstractTableModel {
	ArrayList<Ent> entList;
	
	/******************** CONSTRUCTORS *********************/
	
	EntTableModel(ArrayList<Ent> entList){
		this.entList = entList;
	}	
	
	/******************** NON-STATIC METHODS ********************/
	
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
	
	@Override	
	public void setValueAt(Object value, int r, int c) {
		Ent ent = DataStorage.getEntList().get(r);
		ent.setFieldValue(c, value);
		//JOptionPane.showMessageDialog(null, bank);
	}
	
	public boolean isCellEditable(int r, int c){
		return Ent.isFieldEditable(c);
	}		
}