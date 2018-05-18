package com.zhurylomihaylo.www.entzp;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JOptionPane;

class Ent  implements Serializable, EdiTableObject {
	static int BANK_FIELS_INDEX = 8;
	
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
		Object[][] columnFieldsInfo = new Object[11][];
		columnFieldsInfo[0] = new Object[]{"name", "Найменування", false, true};
		columnFieldsInfo[1] = new Object[]{"net", "Основна сума", true, true};
		columnFieldsInfo[2] = new Object[]{"netPlus1", "Доплата 1", true, true};
		columnFieldsInfo[3] = new Object[]{"netPlus2", "Доплата 2", true, true};
		columnFieldsInfo[4] = new Object[]{"esv", "ЄСВ", true, false};
		columnFieldsInfo[5] = new Object[]{"constTotal", "Всього витрат Нетто", true, false};
		columnFieldsInfo[6] = new Object[]{"addPayment", "% від допл.2", true, false};
		columnFieldsInfo[7] = new Object[]{"tax", "Єдиний податок", true, false};
		columnFieldsInfo[8] = new Object[]{"bank", "Банк", false, true};		
		columnFieldsInfo[9] = new Object[]{"bankServicePayment", "Послуги банку", true, false};
		columnFieldsInfo[10] = new Object[]{"totalAll", "Всього", true, false};
		
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
	
	//@Override
	public void calculate(int rowIndex){
		BigDecimal oneHund = BigDecimal.valueOf(100);
		
		esv = DataStorage.getMinSalary().multiply(DataStorage.getEsvPercent()).divide(oneHund);		
		constTotal = net.add(netPlus1).add(netPlus2).add(esv);
		addPayment = netPlus2.multiply(DataStorage.getAdditionalPercent()).divide(oneHund, 2, RoundingMode.HALF_UP);	
	
		BigDecimal bankConstPayment;
		if (bank == null) {
			totalAll = constTotal.add(addPayment).divide(BigDecimal.valueOf(1).subtract(DataStorage.getTaxPercent().divide(oneHund)), 2, RoundingMode.HALF_UP);
			bankServicePayment = BigDecimal.ZERO;
		}
		else {
			bankConstPayment = bank.monthlyFee;
			bankConstPayment = bankConstPayment.add(bank.avgTransationCount.multiply(bank.transactionComission).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
			bankConstPayment = bankConstPayment.add(bank.avgTransationCount.multiply(bank.transactionFee));
			
			totalAll = constTotal.add(addPayment).add(bankConstPayment).divide(BigDecimal.valueOf(1).subtract(DataStorage.getTaxPercent().divide(oneHund)).subtract(bank.transactionComission.divide(oneHund)), 2, RoundingMode.HALF_UP);
			bankServicePayment = totalAll.multiply(bank.transactionComission).divide(oneHund, 2, RoundingMode.HALF_UP).add(bankConstPayment);
		}
		tax = totalAll.multiply(DataStorage.getTaxPercent()).divide(oneHund, 2, RoundingMode.HALF_UP);
		totalAll = net.add(netPlus1).add(netPlus2).add(esv).add(addPayment).add(tax).add(bankServicePayment);
		//JOptionPane.showMessageDialog(null, rowIndex);
		MainFrame.staticEntTableModelLink.fireTableRowsUpdated(rowIndex, rowIndex);
	}
	
		
}