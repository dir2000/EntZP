package com.zhurylomihaylo.www.entzp;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;

class EdiTableModel extends AbstractTableModel {
	ArrayList<EdiTableObject> listOfObjects;
	Class realClass;
	
	/******************** CONSTRUCTORS *********************/
	
	EdiTableModel(Class realClass, ArrayList<EdiTableObject> listOfObjects){
		this.listOfObjects = listOfObjects;
		this.realClass = realClass;
	}	
	
	/******************** NON-STATIC METHODS ********************/
	
	@Override
	public int getColumnCount() {
		//System.out.println(new Date() + " - getColumnCount - " + EdiTableObject.getFieldsCount(realClass));
		return EdiTableObject.getFieldsCount(realClass);
	}

	@Override
	public int getRowCount() {
		System.out.println(new Date() + " - " + realClass + " - getSelectedColumn - (" + MainFrame.bankTable.getColumnSelectionAllowed() + ")");
		return listOfObjects.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		EdiTableObject obj = listOfObjects.get(arg0);
		return obj.getFieldValue(realClass, arg1);
	}
	
	@Override	
	public void setValueAt(Object value, int r, int c) {
		EdiTableObject obj = listOfObjects.get(r);
		obj.setFieldValue(realClass, c, value);
	}
	
	public boolean isCellEditable(int r, int c){
		return EdiTableObject.isFieldEditable(realClass, c);
	}		
}