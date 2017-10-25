package com.zhurylomihaylo.www.entzp;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;

class Ent  implements Serializable, EdiTableObject {
	static int BANK_FIELS_INDEX = 6;
	
	String name;
	BigDecimal net = BigDecimal.ZERO;
	BigDecimal netPlus1 = BigDecimal.ZERO;
	BigDecimal netPlus2 = BigDecimal.ZERO;
	BigDecimal esv = BigDecimal.ZERO;
	BigDecimal constTotal = BigDecimal.ZERO;	
	BigDecimal addPayment = BigDecimal.ZERO;
	BigDecimal tax = BigDecimal.ZERO;
	Bank bank;
	BigDecimal bankServicePayment = BigDecimal.ZERO;
	BigDecimal totalAll = BigDecimal.ZERO;
	
	static {
		//1. Field name 2. Column header 3. Is numeric 4. Editable
		Object[][] columnFieldsInfo = new Object[9][];
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
		
		classFieldsInfo.put(Ent.class, columnFieldsInfo);
	}
	
	/******************** CONSTRUCTORS ********************/
	Ent() {
		this.name = "Нова особа";
	}

	Ent(String name) {
		this.name = name;
	}

	/******************** NON_STATIC METHODS *********************/

	@Override
	public String toString(){
		return name;
	}
		
}