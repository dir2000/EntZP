package com.zhurylomihaylo.www.entzp;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.UUID;

import javax.swing.JOptionPane;

class Bank implements Serializable {
	private static Object[][] columnFieldsInfo;
	static final int ID_COLUMN = 0;
	
	private UUID id = UUID.randomUUID();
	private String name;
	private BigDecimal monthlyFee = BigDecimal.ZERO;
	private BigDecimal transactionComission = BigDecimal.ZERO;
	private BigDecimal transactionFee = BigDecimal.ZERO;

	static {
		//1. Field
		//2. Column header
		//3. Is numeric
		columnFieldsInfo = new Object[5][];
		columnFieldsInfo[0] = new Object[]{"id", "ID", false};
		columnFieldsInfo[1] = new Object[]{"name", "Найменування", false};
		columnFieldsInfo[2] = new Object[]{"monthlyFee", "Щомісячна плата", true};
		columnFieldsInfo[3] = new Object[]{"transactionComission", "Відсоток за зняття готівки, %", true};
		columnFieldsInfo[4] = new Object[]{"transactionFee", "Плата за зняття готівки, грн.", true};
	}
	
	//STATIC METHODS
	
	static int getFieldsCount() {
		//Field[] fields = Bank.class.getDeclaredFields();
		return columnFieldsInfo.length;
	}
	
	Object getFieldValue(int fieldIndex) {
		try {
			return getClass().getDeclaredField((String) columnFieldsInfo[fieldIndex][0]).get(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	void setFieldValue(int fieldIndex, Object value) {
		try {
			Field field = getClass().getDeclaredField((String) columnFieldsInfo[fieldIndex][0]);
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
	}
	
	static String getFieldHeader(int index){
		return (String) columnFieldsInfo[index][1]; 
	}
	
	static boolean isFieldNumeric(int index){
		return (boolean) columnFieldsInfo[index][2]; 
	}
	
	
	//OVERRIDED METHODS
	@Override
	public String toString(){
		return name;
	}
	
	//CONSTRUCTORS
	
	Bank(String name) {
		this.name = name;
	}
	
	
	//GETTERS AND SETTERS

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	BigDecimal getMonthlyFee() {
		return monthlyFee;
	}

	void setMonthlyFee(BigDecimal monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	BigDecimal getTransactionComission() {
		return transactionComission;
	}

	void setTransactionComission(BigDecimal transactionComission) {
		this.transactionComission = transactionComission;
	}

	BigDecimal getTransactionFee() {
		return transactionFee;
	}

	void setTransactionFee(BigDecimal transactionFee) {
		this.transactionFee = transactionFee;
	}
	
	//OTHER
	
}