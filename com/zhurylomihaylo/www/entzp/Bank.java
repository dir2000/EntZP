package com.zhurylomihaylo.www.entzp;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.UUID;

class Bank {
	private UUID id = UUID.randomUUID();
	private String name;
	private BigDecimal monthlyFee = BigDecimal.ZERO;
	private BigDecimal transactionComission = BigDecimal.ZERO;
	private BigDecimal transactionFee = BigDecimal.ZERO;
	
	static final int ID_COLUMN = 0;
	
	//STATIC METHODS
	
	static int getFieldsCount() {
		Field[] fields = Bank.class.getDeclaredFields();
		return fields.length;
	}
	
	Object getFieldValue(int fieldIndex) {
		try {
			return getClass().getDeclaredFields()[fieldIndex].get(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	

	//CONSTRUCTORS
	
	Bank(){
		
	}
	
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
	
}