package com.zhurylomihaylo.www.entzp;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;

class Ent implements Serializable {
	private static Object[][] columnFieldsInfo;
	private String name;
	private BigDecimal net = BigDecimal.ZERO;
	private BigDecimal netPlus1 = BigDecimal.ZERO;
	private BigDecimal netPlus2 = BigDecimal.ZERO;
	private BigDecimal esv = BigDecimal.ZERO;
	private BigDecimal constTotal = BigDecimal.ZERO;	
	private BigDecimal addPayment = BigDecimal.ZERO;
	private BigDecimal tax = BigDecimal.ZERO;
	private Bank bank;
	private BigDecimal bankServicePayment = BigDecimal.ZERO;
	private BigDecimal totalAll = BigDecimal.ZERO;
	
	static {
		//1. Field name 2. Column header 3. Is numeric 4. Editable
		columnFieldsInfo = new Object[9][];
		columnFieldsInfo[0] = new Object[]{"name", "Найменування", false, true};
		columnFieldsInfo[1] = new Object[]{"netPlus1", "Доплата 1", true, true};
		columnFieldsInfo[2] = new Object[]{"netPlus2", "Доплата 2", true, true};
		columnFieldsInfo[3] = new Object[]{"esv", "ЄСВ", true, false};
		columnFieldsInfo[4] = new Object[]{"constTotal", "Всього витрат Нетто", true, false};
		columnFieldsInfo[5] = new Object[]{"addPayment", "Додаткова оплата, %", true, false};
		columnFieldsInfo[6] = new Object[]{"tax", "Єдиний податок", true, false};
		columnFieldsInfo[6] = new Object[]{"bank", "Банк", false, true};		
		columnFieldsInfo[7] = new Object[]{"bankServicePayment", "Послуги банку", true, false};
		columnFieldsInfo[8] = new Object[]{"totalAll", "Всього", true, false};
				
//		columnFieldsInfo[3] = new Object[]{"transactionComission", "Відсоток за зняття готівки, %", true};
//		columnFieldsInfo[4] = new Object[]{"transactionFee", "Плата за зняття готівки, грн.", true};
	}
	
	/******************** CONSTRUCTORS ********************/
	
	Ent(String name) {
		this.name = name;
	}
	
	/******************** STATIC METHODS ********************/
	
	static int getFieldsCount() {
		//Field[] fields = Bank.class.getDeclaredFields();
		return columnFieldsInfo.length;
	}
	
	static String getFieldHeader(int index){
		return (String) columnFieldsInfo[index][1]; 
	}

	static boolean isFieldNumeric(int index){
		return (boolean) columnFieldsInfo[index][2]; 
	}

	static boolean isFieldEditable(int index){
		return (boolean) columnFieldsInfo[index][3]; 
	}	
	/******************** NON-STATIC METHODS ********************/
	
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

	/******************** GETTERS AND SETTERS ********************/
	
	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	BigDecimal getNet() {
		return net;
	}

	void setNet(BigDecimal net) {
		this.net = net;
	}

	BigDecimal getNetPlus1() {
		return netPlus1;
	}

	void setNetPlus1(BigDecimal netPlus1) {
		this.netPlus1 = netPlus1;
	}

	BigDecimal getNetPlus2() {
		return netPlus2;
	}

	void setNetPlus2(BigDecimal netPlus2) {
		this.netPlus2 = netPlus2;
	}

	BigDecimal getEsv() {
		return esv;
	}

	void setEsv(BigDecimal esv) {
		this.esv = esv;
	}

	BigDecimal getConstTotal() {
		return constTotal;
	}

	void setConstTotal(BigDecimal constTotal) {
		this.constTotal = constTotal;
	}

	BigDecimal getAddPayment() {
		return addPayment;
	}

	void setAddPayment(BigDecimal addPercent) {
		this.addPayment = addPercent;
	}

	BigDecimal getTax() {
		return tax;
	}

	void setTax(BigDecimal taxPercent) {
		this.tax = taxPercent;
	}

	BigDecimal getBankServicePayment() {
		return bankServicePayment;
	}

	void setBankServicePayment(BigDecimal bankServicePayment) {
		this.bankServicePayment = bankServicePayment;
	}

	BigDecimal getTotalAll() {
		return totalAll;
	}

	void setTotalAll(BigDecimal totalAll) {
		this.totalAll = totalAll;
	}
	
	
}