package com.zhurylomihaylo.www.entzp;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.Vector;

class EdiTableModel extends AbstractTableModel {
	Vector<EdiTableObject> listOfObjects;
	Class realClass;
	
	/******************** CONSTRUCTORS *********************/
	
	EdiTableModel(Class realClass, Vector<EdiTableObject> listOfObjects){
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
		//System.out.println(new Date() + " - " + realClass.getSimpleName() + " - getRowSelectionAllowed - (" + MainFrame.bankTable.getRowSelectionAllowed() + ")");
		//System.out.println(new Date() + " - " + MainFrame.bankTable.getSelectedRow());
		return listOfObjects.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		EdiTableObject obj = listOfObjects.get(rowIndex);
		return obj.getFieldValue(realClass, columnIndex);
	}
	
	@Override	
	public void setValueAt(Object value, int r, int c) {
		EdiTableObject obj = listOfObjects.get(r);
		obj.setFieldValue(realClass, r, c, value);
	}
	
	public boolean isCellEditable(int r, int c){
		return EdiTableObject.isFieldEditable(realClass, c);
	}		
}