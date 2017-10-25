package com.zhurylomihaylo.www.entzp;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.UUID;

import javax.swing.JOptionPane;

class Bank implements Serializable, EdiTableObject  
{
	//private static Object[][] columnFieldsInfo;
	static final int ID_COLUMN = 0;
	
	//UUID id = UUID.randomUUID();
	String name;
	BigDecimal monthlyFee = BigDecimal.ZERO;
	BigDecimal transactionComission = BigDecimal.ZERO;
	BigDecimal transactionFee = BigDecimal.ZERO;

	static {
		//1. Field name 2. Column header 3. Is numeric 4. Editable
		Object[][] columnFieldsInfo = new Object[4][];
		//columnFieldsInfo[0] = new Object[]{"id", "ID", false};
		columnFieldsInfo[0] = new Object[]{"name", "Найменування", false, true};
		columnFieldsInfo[1] = new Object[]{"monthlyFee", "Щомісячна плата", true, true};
		columnFieldsInfo[2] = new Object[]{"transactionComission", "Відсоток за зняття готівки, %", true, true};
		columnFieldsInfo[3] = new Object[]{"transactionFee", "Плата за зняття готівки, грн.", true, true};
		classFieldsInfo.put(Bank.class, columnFieldsInfo);
	}
	
	/******************** CONSTRUCTORS *********************/
	
	Bank() {
		this.name = "Новий банк";
	}	
	
	Bank(String name) {
		this.name = name;
	}	
	
	Bank(String name, double monthlyFee, double transactionComission, double transactionFee) {
		this.name = name;
		this.monthlyFee = BigDecimal.valueOf(monthlyFee);
		this.transactionComission = BigDecimal.valueOf(transactionComission);
		this.transactionFee = BigDecimal.valueOf(transactionFee);
	}
	
	/******************** NON_STATIC METHODS *********************/
	
	@Override
	public String toString(){
		return name;
	}
	

	
}