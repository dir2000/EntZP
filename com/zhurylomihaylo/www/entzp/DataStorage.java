package com.zhurylomihaylo.www.entzp;

import java.math.BigDecimal;
import java.util.ArrayList;

class DataStorage {
	private ArrayList<Bank> bankList;

	DataStorage() {
		bankList = new ArrayList<>();

		Bank bank = new Bank("Аваль");
		bank.setMonthlyFee(BigDecimal.valueOf(50D));
		bank.setTransactionComission(BigDecimal.valueOf(0.0085));
		bank.setTransactionFee(BigDecimal.valueOf(5D));
		bankList.add(bank);

		Bank bank2 = new Bank("Приватбанк");
		bank2.setMonthlyFee(BigDecimal.valueOf(30D));
		bank2.setTransactionComission(BigDecimal.valueOf(0.0085));
		bank2.setTransactionFee(BigDecimal.valueOf(5D));
		bankList.add(bank2);
	}
	
	ArrayList<Bank> getBankList(){
		return bankList;
	}	

}