package com.zhurylomihaylo.www.entzp;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;

interface EdiTableObject {
	static final int FIELD_NAME = 0;
	static final int FIELD_HEADER = 1;
	static final int FIELD_IS_NUMERIC = 2;
	static final int FIELD_IS_EDITABLE = 3;
	
	static final HashMap<Class, Object[][]> classFieldsInfo = new HashMap<>();
	
	/******************** STATIC METHODS *********************/
	
	static public int getFieldsCount(Class cl) {
		return classFieldsInfo.get(cl).length;
	}
	
	static public String getFieldHeader(Class cl, int fieldIndex){
		return (String) classFieldsInfo.get(cl)[fieldIndex][FIELD_HEADER]; 
	}

	static public boolean isFieldNumeric(Class cl, int fieldIndex){
		return (boolean) classFieldsInfo.get(cl)[fieldIndex][FIELD_IS_NUMERIC]; 
	}

	static public boolean isFieldEditable(Class cl, int fieldIndex){
		return (boolean) classFieldsInfo.get(cl)[fieldIndex][FIELD_IS_EDITABLE]; 
	}	
	
	/******************** NON_STATIC METHODS *********************/
	
	default Object getFieldValue(Class cl, int fieldIndex) {
		try {
			return cl.getDeclaredField((String) classFieldsInfo.get(cl)[fieldIndex][FIELD_NAME]).get(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	default void setFieldValue(Class cl, int rowIndex, int fieldIndex, Object value) {
		try {
			Field field = cl.getDeclaredField((String) classFieldsInfo.get(cl)[fieldIndex][FIELD_NAME]);
			if (field.getType() == Class.forName("java.math.BigDecimal"))
				field.set(this, BigDecimal.valueOf(Double.parseDouble((String) value)));
			else
				field.set(this, value);
			//.set(this, value);
		} 
		catch (NumberFormatException ex){
			//do nothing
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
		
		calculate(rowIndex);
	}
	
	void calculate(int rowIndex);
}

