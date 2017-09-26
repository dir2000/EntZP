package com.zhurylomihaylo.www.entzp;

import java.io.Serializable;
import java.math.BigDecimal;

class Ent implements Serializable {
	private static Object[][] columnFieldsInfo;
	private String name;
	private BigDecimal net = BigDecimal.ZERO;
	private BigDecimal netPlus1 = BigDecimal.ZERO;
	private BigDecimal netPlus2 = BigDecimal.ZERO;
	private BigDecimal esv = BigDecimal.ZERO;
	private BigDecimal constTotal = BigDecimal.ZERO;	
	private BigDecimal addPercent = BigDecimal.ZERO;
	private BigDecimal taxPercent = BigDecimal.ZERO;
	private BigDecimal bankServicePayment = BigDecimal.ZERO;
	private BigDecimal totalAll = BigDecimal.ZERO;
	
	static {
		//1. Field
		//2. Column header
		//3. Is numeric
		columnFieldsInfo = new Object[9][];
		columnFieldsInfo[0] = new Object[]{"name", "Найменування", false};
		columnFieldsInfo[1] = new Object[]{"netPlus1", "Доплата 1", true};
		columnFieldsInfo[2] = new Object[]{"netPlus2", "Доплата 2", true};
		columnFieldsInfo[3] = new Object[]{"esv", "ЄСВ", true};
		columnFieldsInfo[4] = new Object[]{"constTotal", "Всього витрат Нетто", true};
		columnFieldsInfo[5] = new Object[]{"addPercent", "", true};
		columnFieldsInfo[6] = new Object[]{"taxPercent", "", true};
		columnFieldsInfo[7] = new Object[]{"bankServicePayment", "", true};
		columnFieldsInfo[8] = new Object[]{"totalAll", "", true};
				
//		columnFieldsInfo[3] = new Object[]{"transactionComission", "Відсоток за зняття готівки, %", true};
//		columnFieldsInfo[4] = new Object[]{"transactionFee", "Плата за зняття готівки, грн.", true};
	}

	//STATIC METHODS
	
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

	
	
	Object getFieldValue(int fieldIndex) {
		try {
			return getClass().getDeclaredField((String) columnFieldsInfo[fieldIndex][0]).get(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	//CONSTRUCTORS
	
	Ent(String name) {
		this.name = name;
	}
	
	
	//GETTERS AND SETTERS
	
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

	BigDecimal getAddPercent() {
		return addPercent;
	}

	void setAddPercent(BigDecimal addPercent) {
		this.addPercent = addPercent;
	}

	BigDecimal getTaxPercent() {
		return taxPercent;
	}

	void setTaxPercent(BigDecimal taxPercent) {
		this.taxPercent = taxPercent;
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